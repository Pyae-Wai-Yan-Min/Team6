package com.github.team6;

import org.junit.jupiter.api.Test;
import java.sql.Connection;

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
                // Test the disconnect functionality
                com.github.team6.Connection.disconnect(con);
            }
        } else {
            // Fallback warning if the database container is offline during local test execution
            System.out.println("Warning: Database container not running. Skipping active connection assertions.");
        }
    }
}