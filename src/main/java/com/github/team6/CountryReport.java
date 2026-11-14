package com.github.team6;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CountryReport {

    // Entry point called by Main.java
    public static void generateReport(Connection con) {
        // Issue #24: All countries in the world by population
        ArrayList<World.Country> worldCountries = getCountries(con, null);
        printCountries("*** All Countries in the World by Population ***", null, worldCountries);

        // Issue #25: All countries in a continent by population
        ArrayList<World.Country> continentCountries = getCountries(con, "Continent");
        printCountries("*** All Countries Grouped By Continent ***", "Continent", continentCountries);

        // Issue #26: All countries in a region by population
        ArrayList<World.Country> regionCountries = getCountries(con, "Region");
        printCountries("*** All Countries Grouped By Region ***", "Region", regionCountries);
    }

    // Shared method for retrieving and displaying country data
    public static ArrayList<World.Country> getCountries(Connection con, String groupColumn) {
        String sql = "SELECT c.Code, c.Name, c.Continent, c.Region, " +
                "c.Population, capital.Name AS Capital " +
                "FROM country c LEFT JOIN city capital ON c.Capital = capital.ID ";

        if (groupColumn == null) {
            sql += "ORDER BY c.Population DESC, c.Name ASC";
        } else if (groupColumn.equals("Continent")) {
            sql += "ORDER BY c.Continent ASC, c.Population DESC, c.Name ASC";
        } else {
            sql += "ORDER BY c.Region ASC, c.Population DESC, c.Name ASC";
        }

        ArrayList<World.Country> countries = new ArrayList<>();
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                World.Country country = new World.Country();
                country.setCode(rs.getString("Code"));
                country.setName(rs.getString("Name"));
                country.setContinent(rs.getString("Continent"));
                country.setRegion(rs.getString("Region"));
                country.setPopulation(rs.getInt("Population"));
                country.setCapital(rs.getString("Capital"));
                countries.add(country);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching country report data: " + e.getMessage());
        }
        return countries;
    }

    // Prints a list of countries with grouping support
    public static void printCountries(String reportTitle, String groupColumn, ArrayList<World.Country> countries) {
        if (countries == null || countries.isEmpty()) {
            System.out.println("No countries found for this report.");
            return;
        }

        System.out.println("\n========================================");
        System.out.println(reportTitle);
        System.out.println("========================================");

        String previousGroup = null;
        printColumnHeaders();

        for (World.Country c : countries) {
            if (groupColumn != null) {
                String currentGroup = groupColumn.equals("Continent") ? c.getContinent() : c.getRegion();
                if (previousGroup == null || !java.util.Objects.equals(currentGroup, previousGroup)) {
                    System.out.println("\n--- " + currentGroup + " ---");
                    printColumnHeaders();
                    previousGroup = currentGroup;
                }
            }

            System.out.printf("%-8s %-25s %-20s %-25s %12d %-25s%n",
                    c.getCode(),
                    c.getName(),
                    c.getContinent(),
                    c.getRegion(),
                    c.getPopulation(),
                    c.getCapital() == null ? "Unknown" : c.getCapital()
            );
        }
    }

    // Print header
    private static void printColumnHeaders() {
        System.out.printf("%-8s %-25s %-20s %-25s %12s %-25s%n",
                "Code", "Name", "Continent", "Region", "Population", "Capital");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
    }
}