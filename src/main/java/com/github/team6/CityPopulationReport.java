package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Handles the generation of City-Population demographic reports.
 * This class fulfills the primary task of allowing a Data Analyst to extract
 * the top N populated cities sorted by largest population to smallest.
 */
public class CityPopulationReport {

    /**
     * Triggers the generation of predefined top N city population reports.
     *
     * @param con The active database connection.
     */
    // This is the main method that Main.java will call
    public static void generateReport(Connection con) {
        System.out.println("\n--- Generating City Population Reports ---");

        // You can change the 'N' number and the targets here to test
        getTopCitiesInWorld(con, 5);
        getTopCitiesInContinent(con, "Asia", 5);
        getTopCitiesInRegion(con, "Southeast Asia", 5);

        // New methods for Issue #4 and Issue #5
        getTopCitiesInCountry(con, "United States", 5);
        getTopCitiesInDistrict(con, "California", 5);
    }

    /**
     * Analyzes global demographics by retrieving the top N populated cities in the world.
     *
     * @param con The active database connection.
     * @param n   The number of top records to retrieve.
     */
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

    /**
     * Analyzes continental demographics by retrieving the top N populated cities in a continent.
     *
     * @param con       The active database connection.
     * @param continent The target continent to filter by.
     * @param n         The number of top records to retrieve.
     */
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

    /**
     * Analyzes regional demographics by retrieving the top N populated cities in a region.
     *
     * @param con    The active database connection.
     * @param region The target region to filter by.
     * @param n      The number of top records to retrieve.
     */
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

    /**
     * Analyzes national demographics by retrieving the top N populated cities in a country.
     * Addresses Issue #4.
     *
     * @param con     The active database connection.
     * @param country The target country name to filter by.
     * @param n       The number of top records to retrieve.
     */
    // 4. The top N populated cities in a country
    public static void getTopCitiesInCountry(Connection con, String country, int n) {
        System.out.println("\nTop " + n + " Populated Cities in Country: " + country);
        System.out.println(String.format("%-35s %-35s %-35s %-20s", "Name", "Country", "District", "Population"));
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, country);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-35s %-35s %-35s %-20d",
                        rs.getString("Name"),
                        rs.getString("CountryName"),
                        rs.getString("District"),
                        rs.getInt("Population")));
            }
        } catch (Exception e) {
            System.out.println("Error fetching country cities: " + e.getMessage());
        }
    }

    /**
     * Analyzes local demographics by retrieving the top N populated cities in a district.
     * Addresses Issue #5.
     *
     * @param con      The active database connection.
     * @param district The target district name to filter by.
     * @param n        The number of top records to retrieve.
     */
    // 5. The top N populated cities in a district
    public static void getTopCitiesInDistrict(Connection con, String district, int n) {
        System.out.println("\nTop " + n + " Populated Cities in District: " + district);
        System.out.println(String.format("%-35s %-35s %-35s %-20s", "Name", "Country", "District", "Population"));
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE city.District = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, district);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(String.format("%-35s %-35s %-35s %-20d",
                        rs.getString("Name"),
                        rs.getString("CountryName"),
                        rs.getString("District"),
                        rs.getInt("Population")));
            }
        } catch (Exception e) {
            System.out.println("Error fetching district cities: " + e.getMessage());
        }
    }
}