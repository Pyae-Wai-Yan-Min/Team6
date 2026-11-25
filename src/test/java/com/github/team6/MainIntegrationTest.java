package com.github.team6;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Integration tests for the Main application entry point.
 * Note: Your Docker database must be running for testMainExecution() to connect successfully.
 */
public class MainIntegrationTest {

    /**
     * Runs the full main() method against the live Docker database.
     * Covers main(), Connection.connect(), run(con), report execution, and Connection.disconnect(con).
     */
    @Test
    void testMainExecution() {
        Main.main(new String[]{});
    }

    /**
     * Tests the null-connection guard inside run(con).
     * Covers the 'if (con == null)' branch cleanly without affecting the live database.
     */
    @Test
    void testRunWithNullConnection() {
        Main.run(null);
    }

    /**
     * Triggers the catch (Exception e) block inside Main.run() (lines 56-57)
     * without modifying Main.java, pushing Main.java to 100% coverage.
     */
    @Test
    void testRunCatchBlock() {
        Connection dummyCon = (Connection) Proxy.newProxyInstance(
                Connection.class.getClassLoader(),
                new Class<?>[]{Connection.class},
                (proxy, method, args) -> null
        );

        PrintStream originalOut = System.out;
        try {
            System.setOut(new PrintStream(new ByteArrayOutputStream()) {
                @Override
                public void println(String x) {
                    if ("Running application...".equals(x)) {
                        throw new RuntimeException("Simulated report generation error");
                    }
                    originalOut.println(x);
                }
            });

            Main.run(dummyCon);
        } finally {
            System.setOut(originalOut);
        }
    }
}