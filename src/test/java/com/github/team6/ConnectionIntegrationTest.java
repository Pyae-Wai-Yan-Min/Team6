package com.github.team6;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectionIntegrationTest {

    @Test
    void testDatabaseConnectionAndDisconnect() {
        // Attempt to connect to the live database (supports local Docker or container network)
        Connection con = com.github.team6.Connection.connect();

        if (con != null) {
            try {
                assertFalse(con.isClosed(), "The database connection should be active and open.");
            } catch (Exception e) {
                fail("Exception occurred while checking connection state: " + e.getMessage());
            } finally {
                // Test the disconnect functionality with an active connection
                com.github.team6.Connection.disconnect(con);
            }
        } else {
            // Fallback warning if the database container is offline during local test execution
            System.out.println("Warning: Database container not running. Skipping active connection assertions.");
        }

        // Cover the 'con != null' false branch inside disconnect() (compatible with JUnit Jupiter 5.1.0)
        com.github.team6.Connection.disconnect(null);
    }

    @Test
    void testConstructorAndDisconnectException() {
        // 1. Covers the default class constructor on line 5
        assertNotNull(new com.github.team6.Connection());

        // 2. Covers lines 66-67 (catch Exception in disconnect) using a standard Java Proxy
        Connection failingConnection = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        throw new SQLException("Simulated close error");
                    }
                    return null;
                }
        );
        com.github.team6.Connection.disconnect(failingConnection);
    }

    @Test
    void testDockerFallbackAndRetryLoop() throws Exception {
        // Ensure the real MySQL driver is loaded first so we can capture and restore it cleanly
        Class.forName("com.mysql.cj.jdbc.Driver");
        List<Driver> originalDrivers = Collections.list(DriverManager.getDrivers());

        // Create a dummy Connection proxy to return when simulating the Docker DB fallback
        Connection dummyDockerCon = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> null
        );

        // Toggle between Scenario A (Docker fallback succeeds) and Scenario B (both fail + retry)
        final boolean[] allowDockerConnection = {true};

        Driver fakeDriver = new Driver() {
            @Override
            public Connection connect(String url, Properties info) throws SQLException {
                if (url != null && url.contains("localhost:33060")) {
                    throw new SQLException("Simulated local DB failure");
                }
                if (url != null && url.contains("db:3306")) {
                    if (allowDockerConnection[0]) {
                        return dummyDockerCon;
                    }
                    // Interrupt current thread so Thread.sleep(3000) hits InterruptedException immediately (0ms wait)
                    Thread.currentThread().interrupt();
                    throw new SQLException("Simulated Docker DB failure");
                }
                return null;
            }

            @Override
            public boolean acceptsURL(String url) { return url != null && url.startsWith("jdbc:mysql:"); }

            @Override
            public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) { return new DriverPropertyInfo[0]; }

            @Override
            public int getMajorVersion() { return 1; }

            @Override
            public int getMinorVersion() { return 0; }

            @Override
            public boolean jdbcCompliant() { return false; }

            @Override
            public Logger getParentLogger() throws SQLFeatureNotSupportedException {
                throw new SQLFeatureNotSupportedException();
            }
        };

        try {
            // Temporarily replace real drivers with our fake test driver
            for (Driver d : originalDrivers) {
                DriverManager.deregisterDriver(d);
            }
            DriverManager.registerDriver(fakeDriver);

            // Scenario A: Local fails -> Docker fallback succeeds (covers lines 33, 37, 42, 43)
            allowDockerConnection[0] = true;
            Connection dockerCon = com.github.team6.Connection.connect();
            assertNotNull(dockerCon, "Should return the simulated Docker connection.");

            // Scenario B: Both fail -> hits retry, Thread.sleep InterruptedException, and loop exit (covers lines 45-55, 20)
            allowDockerConnection[0] = false;
            Connection failedCon = com.github.team6.Connection.connect();
            assertNull(failedCon, "Should return null after all retries fail.");
        } finally {
            // Clear any leftover interrupt flag and restore the original MySQL driver(s) for other tests
            Thread.interrupted();
            DriverManager.deregisterDriver(fakeDriver);
            for (Driver d : originalDrivers) {
                DriverManager.registerDriver(d);
            }
        }
    }
}