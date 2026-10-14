package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryPopulationReport {

    // Entry point called by Main.java
    public static void generateReport(Connection con) {
        System.out.println("\n--- Generating Country Population Reports ---");

        // Report 1: The top N populated countries in the world
        getTopCountriesInWorld(con, 5);

        // Report 2: The top N populated countries in a continent
        getTopCountriesInContinent(con, "Asia", 5);

        // Report 3: The top N populated countries in a region
        getTopCountriesInRegion(con, "Southeast Asia", 5);
    }

    // Helper method to print a standardized table header
    private static void printTableHeader() {
        System.out.println(String.format("%-5s | %-18s | %-15s | %-26s | %-13s | %-8s",
                "Code", "Name", "Continent", "Region", "Population", "Capital"));
        System.out.println("-".repeat(98));
    }

    // Report 1: Worldwide top N populated countries report
    public static void getTopCountriesInWorld(Connection con, int n) {
        System.out.println("\nTop " + n + " Populated Countries in the World:");
        printTableHeader();
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-5s | %-18s | %-15s | %-26s | %,13d | %-8d",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getInt("Capital")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching world countries: " + e.getMessage());
        }
    }

    // Report 2: Top N populated countries in a specific continent
    public static void getTopCountriesInContinent(Connection con, String continent, int n) {
        System.out.println("\nTop " + n + " Populated Countries in " + continent + ":");
        printTableHeader();
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Continent = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-5s | %-18s | %-15s | %-26s | %,13d | %-8d",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getInt("Capital")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching continent countries: " + e.getMessage());
        }
    }

    // Report 3: Top N populated countries in a specific region
    public static void getTopCountriesInRegion(Connection con, String region, int n) {
        System.out.println("\nTop " + n + " Populated Countries in " + region + ":");
        printTableHeader();
        try {
            String query = "SELECT Code, Name, Continent, Region, Population, Capital FROM country WHERE Region = ? ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-5s | %-18s | %-15s | %-26s | %,13d | %-8d",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getInt("Capital")));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching region countries: " + e.getMessage());
        }
    }
}