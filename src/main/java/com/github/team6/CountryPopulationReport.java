package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryPopulationReport {

    /**
     * Generates all required Country Population Reports in one clean execution.
     *
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating Country Population Reports ---\n");

        // Issue #17: Top N populated countries in the world
        ArrayList<World.Country> worldCountries = getTopCountriesInWorld(con, 5);
        printCountries("Top 5 Populated Countries in the World", worldCountries);

        // Issue #18: Top N populated countries in a continent
        ArrayList<World.Country> continentCountries = getTopCountriesInContinent(con, "Asia", 5);
        printCountries("Top 5 Populated Countries in Asia", continentCountries);

        // Issue #19: Top N populated countries in a region
        ArrayList<World.Country> regionCountries = getTopCountriesInRegion(con, "Southeast Asia", 5);
        printCountries("Top 5 Populated Countries in Southeast Asia", regionCountries);
    }

    /**
     * Issue #17: Gets the top N populated countries in the world ordered by population descending.
     */
    public static ArrayList<World.Country> getTopCountriesInWorld(Connection con, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<World.Country> countries = new ArrayList<>();
            while (rs.next()) {
                World.Country cp = new World.Country();
                cp.setCode(rs.getString("Code"));
                cp.setName(rs.getString("Name"));
                cp.setContinent(rs.getString("Continent"));
                cp.setRegion(rs.getString("Region"));
                cp.setPopulation(rs.getInt("Population"));
                cp.setCapital(rs.getString("Capital"));
                countries.add(cp);
            }
            return countries;
        } catch (SQLException e) {
            System.out.println("Error fetching world countries: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #18: Gets the top N populated countries in a specific continent ordered by population descending.
     */
    public static ArrayList<World.Country> getTopCountriesInContinent(Connection con, String continent, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<World.Country> countries = new ArrayList<>();
            while (rs.next()) {
                World.Country cp = new World.Country();
                cp.setCode(rs.getString("Code"));
                cp.setName(rs.getString("Name"));
                cp.setContinent(rs.getString("Continent"));
                cp.setRegion(rs.getString("Region"));
                cp.setPopulation(rs.getInt("Population"));
                cp.setCapital(rs.getString("Capital"));
                countries.add(cp);
            }
            return countries;
        } catch (SQLException e) {
            System.out.println("Error fetching continent countries: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #19: Gets the top N populated countries in a specific region ordered by population descending.
     */
    public static ArrayList<World.Country> getTopCountriesInRegion(Connection con, String region, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<World.Country> countries = new ArrayList<>();
            while (rs.next()) {
                World.Country cp = new World.Country();
                cp.setCode(rs.getString("Code"));
                cp.setName(rs.getString("Name"));
                cp.setContinent(rs.getString("Continent"));
                cp.setRegion(rs.getString("Region"));
                cp.setPopulation(rs.getInt("Population"));
                cp.setCapital(rs.getString("Capital"));
                countries.add(cp);
            }
            return countries;
        } catch (SQLException e) {
            System.out.println("Error fetching region countries: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prints a list of countries with a dynamic title and cleanly formatted columns.
     *
     * @param title     The title to display above the report.
     * @param countries The list of countries to print.
     */
    public static void printCountries(String title, ArrayList<World.Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries found for this report.");
            return;
        }

        System.out.println("\n=======================================================================================================");
        System.out.println("   " + title);
        System.out.println("=======================================================================================================");
        System.out.println(String.format("%-6s | %-18s | %-15s | %-26s | %-14s | %-8s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));
        System.out.println("-------------------------------------------------------------------------------------------------------");

        for (World.Country cp : countries) {
            if (cp == null) continue;

            String codeStr = (cp.getCode() != null) ? cp.getCode() : "-";
            String nameStr = (cp.getName() != null) ? cp.getName() : "-";
            String continentStr = (cp.getContinent() != null) ? cp.getContinent() : "-";
            String regionStr = (cp.getRegion() != null) ? cp.getRegion() : "-";
            String capitalStr = (cp.getCapital() != null) ? cp.getCapital() : "-";

            System.out.println(String.format("%-6s | %-18s | %-15s | %-26s | %,14d | %-8s",
                    codeStr, nameStr, continentStr, regionStr, cp.getPopulation(), capitalStr));
        }
        System.out.println();
    }
}