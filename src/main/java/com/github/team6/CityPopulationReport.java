package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CityPopulationReport {

    // This is the main method that Main.java will call
    public static void generateReport(Connection con) {
        System.out.println("\n--- Generating City Population Reports ---");

        // You can change the 'N' number and the Continent/Region names here to test
        getTopCitiesInWorld(con, 5);
        getTopCitiesInContinent(con, "Asia", 5);
        getTopCitiesInRegion(con, "Southeast Asia", 5);
    }

    // 1. The top N populated cities in the world
    public static void getTopCitiesInWorld(Connection con, int n) {
        System.out.println("\nTop " + n + " Populated Cities in the World:");
        try {
            String query = "SELECT Name, Population FROM city ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Name") + " - Population: " + rs.getInt("Population"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching world cities: " + e.getMessage());
        }
    }

    // 2. The top N populated cities in a continent
    public static void getTopCitiesInContinent(Connection con, String continent, int n) {
        System.out.println("\nTop " + n + " Populated Cities in " + continent + ":");
        try {
            // Requires a JOIN because 'Continent' is stored in the 'country' table
            String query = "SELECT city.Name, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Name") + " - Population: " + rs.getInt("Population"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching continent cities: " + e.getMessage());
        }
    }

    // 3. The top N populated cities in a region
    public static void getTopCitiesInRegion(Connection con, String region, int n) {
        System.out.println("\nTop " + n + " Populated Cities in " + region + ":");
        try {
            // Requires a JOIN because 'Region' is stored in the 'country' table
            String query = "SELECT city.Name, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("Name") + " - Population: " + rs.getInt("Population"));
            }
        } catch (Exception e) {
            System.out.println("Error fetching region cities: " + e.getMessage());
        }
    }
}