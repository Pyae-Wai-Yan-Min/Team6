package com.github.team6;

/**
 * Entry point for the Team 6 World Population Reporting application.
 */
public class Main {

    // Private constructor prevents instantiation and keeps JaCoCo class coverage at 100%
    private Main() {}

    /**
     * Application entry point. Establishes a database connection and delegates to run().
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        // 1. Establish the database connection
        java.sql.Connection con = Connection.connect();
        run(con);
    }

    /**
     * Executes the application reporting workflow using the provided database connection.
     * Extracted from main() so both active and null-connection paths can be integration tested.
     *
     * @param con The active database connection, or null if the connection failed.
     */
    public static void run(java.sql.Connection con) {
        if (con == null) {
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }

        try {
            System.out.println("Running application...");

            // 2. Call each team member's specific report file
//            System.out.println("--- Generating Capital City Reports ---");
//            CapitalCityReport.generateReport(con);
//
//            System.out.println("--- Generating City Reports ---");
//            CityReport.generateReport(con);

            System.out.println("--- Generating City Population Reports ---");
            CityPopulationReport.generateReport(con);

//            System.out.println("--- Generating Country Population Reports ---");
//            CountryPopulationReport.generateReport(con);
//
//            System.out.println("--- Generating Country Reports ---");
//            CountryReport.generateReport(con);
//
//            System.out.println("--- Generating General Population Reports ---");
//            PopulationReport.generateReport(con);

        } catch (Exception e) {
            System.out.println("An error occurred during report generation: " + e.getMessage());
        } finally {
            // 3. Always close the connection when finished
            Connection.disconnect(con);
        }
    }
}