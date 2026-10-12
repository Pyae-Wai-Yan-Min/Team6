package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryPopulationReport {

    // Entry point called by Main.java
    public static void generateReport(Connection con) {
        System.out.println("\n--- Generating Country Population Reports ---");

        // You can change the 'N' number and the Continent/Region names here to test
        // Report 1: The top N populated countries in the world
        getTopCountriesInWorld(con, 5);

        // Report 2: The top N populated countries in a continent
        getTopCountriesInContinent(con, "Asia", 5);

        // Report 3: The top N populated countries in a region
        getTopCountriesInRegion(con, "Southeast Asia", 5);
    }

    // Report 1: Worldwide top N populated countries report
    public static void getTopCountriesInWorld(Connection con, int n) {
        System.out.println("\nTop " + n + " Populated Countries in the World:");
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Code: " + rs.getString("Code") +
                        ", Name: " + rs.getString("Name") +
                        ", Continent: " + rs.getString("Continent") +
                        ", Region: " + rs.getString("Region") +
                        ", Population: " + rs.getInt("Population") +
                        ", Capital: " + rs.getInt("Capital"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching world countries: " + e.getMessage());
        }
    }

    // Report 2: Top N populated countries in a specific continent
    public static void getTopCountriesInContinent(Connection con, String continent, int n) {
        System.out.println("\nTop " + n + " Populated Countries in " + continent + ":");
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Code: " + rs.getString("Code") +
                        ", Name: " + rs.getString("Name") +
                        ", Continent: " + rs.getString("Continent") +
                        ", Region: " + rs.getString("Region") +
                        ", Population: " + rs.getInt("Population") +
                        ", Capital: " + rs.getInt("Capital"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching continent countries: " + e.getMessage());
        }
    }

    // Report 3: Top N populated countries in a specific region
    public static void getTopCountriesInRegion(Connection con, String region, int n) {
        System.out.println("\nTop " + n + " Populated Countries in " + region + ":");
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Code: " + rs.getString("Code") +
                        ", Name: " + rs.getString("Name") +
                        ", Continent: " + rs.getString("Continent") +
                        ", Region: " + rs.getString("Region") +
                        ", Population: " + rs.getInt("Population") +
                        ", Capital: " + rs.getInt("Capital"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching region countries: " + e.getMessage());
        }
    }
}