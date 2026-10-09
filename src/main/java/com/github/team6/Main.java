package com.github.team6;

public class Main {

    public static void main(String[] args){
        // 1. Establish the database connection
        java.sql.Connection con = Connection.connect();

        if (con == null){
            System.out.println("Failed to connect to the database. Exiting.");
            return;
        }

        try{
            System.out.println("Running application...");

            // 2. Call each team member's specific report file
            System.out.println("--- Generating Capital City Reports ---");
//            CapitalCityReport.generateReport(con);

            System.out.println("--- Generating City Reports ---");
//            CityReport.generateReport(con);

            System.out.println("--- Generating City Population Reports ---");
//            CityPopulationReport.generateReport(con);

            System.out.println("--- Generating Country Population Reports ---");
//            CountryPopulationReport.generateReport(con);

            System.out.println("--- Generating Country Reports ---");
//            CountryReport.generateReport(con);

            System.out.println("--- Generating General Population Reports ---");
//            PopulationReport.generateReport(con);

        } catch (Exception e) {
            System.out.println("An error occurred during report generation: " + e.getMessage());
        } finally {
            // 3. Always close the connection when finished
            Connection.disconnect(con);
        }
    }
}