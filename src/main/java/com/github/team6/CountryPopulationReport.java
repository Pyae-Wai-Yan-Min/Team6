package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CountryPopulationReport {

    /**
     * Represents a Country Population Report entry with Code, Name, Continent, Region, Population, and Capital.
     */
    public static class CountryPopulation {
        public String code;
        public String name;
        public String continent;
        public String region;
        public int population;
        public int capital;
    }

    /**
     * Generates all required Country Population Reports in one clean execution.
     *
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating Country Population Reports ---\n");

        // Issue #17: Top N populated countries in the world
        ArrayList<CountryPopulation> worldCountries = getTopCountriesInWorld(con, 5);
        printCountries("Top 5 Populated Countries in the World", worldCountries);

        // Issue #18: Top N populated countries in a continent
        ArrayList<CountryPopulation> continentCountries = getTopCountriesInContinent(con, "Asia", 5);
        printCountries("Top 5 Populated Countries in Asia", continentCountries);

        // Issue #19: Top N populated countries in a region
        ArrayList<CountryPopulation> regionCountries = getTopCountriesInRegion(con, "Southeast Asia", 5);
        printCountries("Top 5 Populated Countries in Southeast Asia", regionCountries);
    }

    /**
     * Issue #17: Gets the top N populated countries in the world ordered by population descending.
     */
    public static ArrayList<CountryPopulation> getTopCountriesInWorld(Connection con, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<CountryPopulation> countries = new ArrayList<>();
            while (rs.next()) {
                CountryPopulation cp = new CountryPopulation();
                cp.code = rs.getString("Code");
                cp.name = rs.getString("Name");
                cp.continent = rs.getString("Continent");
                cp.region = rs.getString("Region");
                cp.population = rs.getInt("Population");
                cp.capital = rs.getInt("Capital");
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
    public static ArrayList<CountryPopulation> getTopCountriesInContinent(Connection con, String continent, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<CountryPopulation> countries = new ArrayList<>();
            while (rs.next()) {
                CountryPopulation cp = new CountryPopulation();
                cp.code = rs.getString("Code");
                cp.name = rs.getString("Name");
                cp.continent = rs.getString("Continent");
                cp.region = rs.getString("Region");
                cp.population = rs.getInt("Population");
                cp.capital = rs.getInt("Capital");
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
    public static ArrayList<CountryPopulation> getTopCountriesInRegion(Connection con, String region, int n) {
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<CountryPopulation> countries = new ArrayList<>();
            while (rs.next()) {
                CountryPopulation cp = new CountryPopulation();
                cp.code = rs.getString("Code");
                cp.name = rs.getString("Name");
                cp.continent = rs.getString("Continent");
                cp.region = rs.getString("Region");
                cp.population = rs.getInt("Population");
                cp.capital = rs.getInt("Capital");
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
    public static void printCountries(String title, ArrayList<CountryPopulation> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries found for this report.");
            return;
        }

        // Distinct visual separation for the title
        System.out.println("\n=======================================================================================================");
        System.out.println("   " + title);
        System.out.println("=======================================================================================================");

        // Print header
        System.out.println(String.format("%-6s | %-18s | %-15s | %-26s | %-14s | %-8s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));
        System.out.println("-------------------------------------------------------------------------------------------------------");

        // Print country rows
        for (CountryPopulation cp : countries) {
            if (cp == null) continue;

            String codeStr = (cp.code != null) ? cp.code : "-";
            String nameStr = (cp.name != null) ? cp.name : "-";
            String continentStr = (cp.continent != null) ? cp.continent : "-";
            String regionStr = (cp.region != null) ? cp.region : "-";

            System.out.println(String.format("%-6s | %-18s | %-15s | %-26s | %,14d | %-8d",
                    codeStr,
                    nameStr,
                    continentStr,
                    regionStr,
                    cp.population,
                    cp.capital));
        }
        System.out.println();
    }
}