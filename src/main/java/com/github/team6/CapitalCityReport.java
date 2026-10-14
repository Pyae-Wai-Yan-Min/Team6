package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CapitalCityReport {

    /**
     * Represents a Capital City with Name, Country, and Population.
     */
    public static class CapitalCity {
        public String name;
        public String country;
        public int population;
    }

    /**
     * Generates all required Capital City Reports in one clean execution.
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating Capital City Reports ---\n");

        // Issue #11: All capital cities in the world by population
        ArrayList<CapitalCity> worldCapitals = getAllCapitalCitiesInWorld(con);
        printCapitalCities("*** All Capital Cities in the World by Population ***", worldCapitals);

        // Issue #12: All capital cities in a continent by population
        ArrayList<CapitalCity> europeCapitals = getAllCapitalCitiesInContinent(con, "Europe");
        printCapitalCities("*** All Capital Cities in Europe by Population ***", europeCapitals);

        // Issue #13: All capital cities in a region by population
        ArrayList<CapitalCity> westernEuropeCapitals = getAllCapitalCitiesInRegion(con, "Western Europe");
        printCapitalCities("*** All Capital Cities in Western Europe by Population ***", westernEuropeCapitals);
    }

    /**
     * Issue #11: Gets all capital cities in the world ordered by population descending.
     */
    public static ArrayList<CapitalCity> getAllCapitalCitiesInWorld(Connection con) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.ID = country.Capital " +
                            "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<CapitalCity> capitals = new ArrayList<>();

            while (rset.next()) {
                CapitalCity cap = new CapitalCity();
                cap.name = rset.getString("City");
                cap.country = rset.getString("Country");
                cap.population = rset.getInt("Population");
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details for the world");
            return null;
        }
    }

    /**
     * Issue #12: Gets all capital cities in a given continent ordered by population descending.
     */
    public static ArrayList<CapitalCity> getAllCapitalCitiesInContinent(Connection con, String continent) {
        try {
            String strSelect =
                    "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.ID = country.Capital " +
                            "WHERE country.Continent = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, continent);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<CapitalCity> capitals = new ArrayList<>();

            while (rset.next()) {
                CapitalCity cap = new CapitalCity();
                cap.name = rset.getString("City");
                cap.country = rset.getString("Country");
                cap.population = rset.getInt("Population");
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details for continent: " + continent);
            return null;
        }
    }

    /**
     * Issue #13: Gets all capital cities in a given region ordered by population descending.
     */
    public static ArrayList<CapitalCity> getAllCapitalCitiesInRegion(Connection con, String region) {
        try {
            String strSelect =
                    "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                            "FROM city " +
                            "JOIN country ON city.ID = country.Capital " +
                            "WHERE country.Region = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, region);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<CapitalCity> capitals = new ArrayList<>();

            while (rset.next()) {
                CapitalCity cap = new CapitalCity();
                cap.name = rset.getString("City");
                cap.country = rset.getString("Country");
                cap.population = rset.getInt("Population");
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get capital city details for region: " + region);
            return null;
        }
    }

    /**
     * Prints a list of capital cities with a dynamic title and cleanly formatted columns.
     *
     * @param title    The title to display above the report.
     * @param capitals The list of capital cities to print.
     */
    public static void printCapitalCities(String title, ArrayList<CapitalCity> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found for this report.");
            return;
        }

        // Distinct visual separation for the title
        System.out.println("\n===========================================================================================");
        System.out.println("   " + title);
        System.out.println("===========================================================================================");

        // Print header
        System.out.println(String.format("%-35s %-40s %-15s", "Capital City", "Country", "Population"));
        System.out.println("-------------------------------------------------------------------------------------------");

        // Print capital city rows
        for (CapitalCity cap : capitals) {
            if (cap == null) continue;

            // Replaces null fields with a dash to prevent formatting crashes
            String nameString = (cap.name != null) ? cap.name : "-";
            String countryString = (cap.country != null) ? cap.country : "-";

            // Uses %,d to automatically add commas to the population integer
            System.out.println(String.format("%-35s %-40s %,15d",
                    nameString, countryString, cap.population));
        }
        System.out.println(); // Adds an empty line after the report finishes
    }
}