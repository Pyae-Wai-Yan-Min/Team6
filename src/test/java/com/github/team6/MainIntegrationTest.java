package com.github.team6;

import org.junit.jupiter.api.Test;

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
}