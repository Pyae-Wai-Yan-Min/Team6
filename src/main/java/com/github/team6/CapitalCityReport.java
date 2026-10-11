package com.github.team6;

import java.sql.*;

public class CapitalCityReport {

    /**
     * Main entry point called from Main.java.
     * Runs all capital city reports.
     */
    public static void generateReport(java.sql.Connection con) {

        // Report 1 — All capital cities in the world
        System.out.println();
        System.out.println("REPORT 1: ALL CAPITAL CITIES IN THE WORLD");
        System.out.println("==========================================");
        reportAllCapitalCitiesWorld(con);

        // Report 2 — Capital cities in every continent
        System.out.println();
        System.out.println("REPORT 2: CAPITAL CITIES BY CONTINENT");
        System.out.println("=====================================");
        String[] continents = {
                "Africa", "Asia", "Europe", "North America", "Oceania", "South America"
        };
        for (String continent : continents) {
            reportCapitalCitiesByContinent(con, continent);
        }

        // Report 3 — Capital cities in every region
        System.out.println();
        System.out.println("REPORT 3: CAPITAL CITIES BY REGION");
        System.out.println("===================================");
        String[] regions = {
                "Caribbean", "Central America", "North America", "South America",
                "Eastern Africa", "Middle Africa", "Northern Africa", "Southern Africa",
                "Western Africa", "Eastern Asia", "Southern and Central Asia",
                "Southeast Asia", "Middle East",
                "Eastern Europe", "Northern Europe", "Southern Europe", "Western Europe",
                "Australia and New Zealand", "Melanesia", "Micronesia", "Polynesia",
                "Baltic Countries", "British Islands", "Nordic Countries"
        };
        for (String region : regions) {
            reportCapitalCitiesByRegion(con, region);
        }
    }

    /**
     * Report 1: All capital cities in the world by population (largest first).
     * Handles GitHub Issue #11.
     */
    public static void reportAllCapitalCitiesWorld(java.sql.Connection con) {
        String sql =
                "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                        "FROM city " +
                        "JOIN country ON city.ID = country.Capital " +
                        "ORDER BY city.Population DESC";

        runReport(con, sql, "All Capital Cities in the World (by population)");
    }

    /**
     * Report 2: All capital cities in a specific continent by population (largest first).
     * Handles GitHub Issue #12.
     */
    public static void reportCapitalCitiesByContinent(java.sql.Connection con, String continent) {
        String sql =
                "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                        "FROM city " +
                        "JOIN country ON city.ID = country.Capital " +
                        "WHERE country.Continent = ? " +
                        "ORDER BY city.Population DESC";

        runReportWithParam(con, sql, continent, "Capital Cities in " + continent + " (by population)");
    }

    /**
     * Report 3: All capital cities in a specific region by population (largest first).
     * Handles GitHub Issue #13.
     */
    public static void reportCapitalCitiesByRegion(java.sql.Connection con, String region) {
        String sql =
                "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                        "FROM city " +
                        "JOIN country ON city.ID = country.Capital " +
                        "WHERE country.Region = ? " +
                        "ORDER BY city.Population DESC";

        runReportWithParam(con, sql, region, "Capital Cities in " + region + " (by population)");
    }


    // ---------- Helpers ----------

    private static void runReport(java.sql.Connection con, String sql, String title) {
        try {
            Statement stmt = con.createStatement();
            ResultSet rset = stmt.executeQuery(sql);
            printResults(rset, title);
        } catch (SQLException e) {
            System.out.println("Error generating capital city report: " + e.getMessage());
        }
    }

    private static void runReportWithParam(java.sql.Connection con, String sql, String param, String title) {
        try {
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, param);
            ResultSet rset = pstmt.executeQuery();
            printResults(rset, title);
        } catch (SQLException e) {
            System.out.println("Error generating capital city report: " + e.getMessage());
        }
    }

    private static void printResults(ResultSet rset, String title) throws SQLException {
        System.out.println();
        System.out.println("=== " + title + " ===");
        System.out.printf("%-35s %-40s %-12s%n", "Capital City", "Country", "Population");
        System.out.println("--------------------------------------------------------------------------------");

        int count = 0;
        while (rset.next()) {
            System.out.printf("%-35s %-40s %-12d%n",
                    rset.getString("City"),
                    rset.getString("Country"),
                    rset.getInt("Population"));
            count++;
        }
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Total: " + count + " rows");
        System.out.println();
    }
}