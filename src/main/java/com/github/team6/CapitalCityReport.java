package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CapitalCityReport {

    /**
     * Generates all required Capital City Reports in one clean execution.
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating Capital City Reports ---\n");

        // Issue #11: All capital cities in the world by population
        ArrayList<World.CapitalCity> worldCapitals = getAllCapitalCitiesInWorld(con);
        printCapitalCities("*** All Capital Cities in the World by Population ***", worldCapitals);

        // Issue #12: All capital cities in a continent by population
        ArrayList<World.CapitalCity> europeCapitals = getAllCapitalCitiesInContinent(con, "Europe");
        printCapitalCities("*** All Capital Cities in Europe by Population ***", europeCapitals);

        // Issue #13: All capital cities in a region by population
        ArrayList<World.CapitalCity> westernEuropeCapitals = getAllCapitalCitiesInRegion(con, "Western Europe");
        printCapitalCities("*** All Capital Cities in Western Europe by Population ***", westernEuropeCapitals);

        // Issue #14: Top 10 capital cities in the world
        ArrayList<World.CapitalCity> topNWorld = getTopNCapitalCitiesInWorld(con, 10);
        printCapitalCities("*** Top 10 Capital Cities in the World by Population ***", topNWorld);

        // Issue #15: Top 10 capital cities in a continent
        ArrayList<World.CapitalCity> topNEurope = getTopNCapitalCitiesInContinent(con, "Europe", 10);
        printCapitalCities("*** Top 10 Capital Cities in Europe by Population ***", topNEurope);

        // Issue #16: Top 10 capital cities in a region
        ArrayList<World.CapitalCity> topNWesternEurope = getTopNCapitalCitiesInRegion(con, "Western Europe", 10);
        printCapitalCities("*** Top 10 Capital Cities in Western Europe by Population ***", topNWesternEurope);
    }

    /**
     * Issue #11: Gets all capital cities in the world ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getAllCapitalCitiesInWorld(Connection con) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get capital city details for the world: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #12: Gets all capital cities in a given continent ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getAllCapitalCitiesInContinent(Connection con, String continent) {
        try {
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital WHERE country.Continent = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, continent);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get capital city details for continent: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #13: Gets all capital cities in a given region ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getAllCapitalCitiesInRegion(Connection con, String region) {
        try {
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital WHERE country.Region = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, region);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get capital city details for region: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #14: Gets Top N capital cities in the world ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getTopNCapitalCitiesInWorld(Connection con, int n) {
        try {
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setInt(1, n);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get top N capital city details for the world: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #15: Gets Top N capital cities in a given continent ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getTopNCapitalCitiesInContinent(Connection con, String continent, int n) {
        try {
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get top N capital city details for continent: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #16: Gets Top N capital cities in a given region ordered by population descending.
     */
    public static ArrayList<World.CapitalCity> getTopNCapitalCitiesInRegion(Connection con, String region, int n) {
        try {
            String strSelect = "SELECT city.Name AS City, country.Name AS Country, city.Population AS Population " +
                    "FROM city JOIN country ON city.ID = country.Capital WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.CapitalCity> capitals = new ArrayList<>();
            while (rset.next()) {
                World.CapitalCity cap = new World.CapitalCity();
                cap.setName(rset.getString("City"));
                cap.setCountry(rset.getString("Country"));
                cap.setPopulation(rset.getInt("Population"));
                capitals.add(cap);
            }
            return capitals;
        } catch (Exception e) {
            System.out.println("Failed to get top N capital city details for region: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prints a list of capital cities with a dynamic title and cleanly formatted columns.
     *
     * @param title    The title to display above the report.
     * @param capitals The list of capital cities to print.
     */
    public static void printCapitalCities(String title, ArrayList<World.CapitalCity> capitals) {
        if (capitals == null || capitals.isEmpty()) {
            System.out.println("No capital cities found for this report.");
            return;
        }
        System.out.println("\n===========================================================================================");
        System.out.println("   " + title);
        System.out.println("===========================================================================================");
        System.out.println(String.format("%-35s %-40s %-15s", "Capital City", "Country", "Population"));
        System.out.println("-------------------------------------------------------------------------------------------");

        for (World.CapitalCity cap : capitals) {
            if (cap == null) continue;
            String nameString = (cap.getName() != null) ? cap.getName() : "-";
            String countryString = (cap.getCountry() != null) ? cap.getCountry() : "-";
            System.out.println(String.format("%-35s %-40s %,15d", nameString, countryString, cap.getPopulation()));
        }
        System.out.println();
    }
}