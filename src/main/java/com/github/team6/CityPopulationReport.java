package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
    public static void generateReport(Connection con) {
        System.out.println("\n--- Generating City Population Reports ---");

        // 1. The top N populated cities in the world
        ArrayList<World.City> worldCities = getTopCitiesInWorld(con, 5);
        printCities("\nTop 5 Populated Cities in the World:", worldCities);

        // 2. The top N populated cities in a continent
        ArrayList<World.City> continentCities = getTopCitiesInContinent(con, "Asia", 5);
        printCities("\nTop 5 Populated Cities in Asia:", continentCities);

        // 3. The top N populated cities in a region
        ArrayList<World.City> regionCities = getTopCitiesInRegion(con, "Southeast Asia", 5);
        printCities("\nTop 5 Populated Cities in Southeast Asia:", regionCities);

        // 4. New methods for Issue #4
        ArrayList<World.City> countryCities = getTopCitiesInCountry(con, "United States", 5);
        printCities("\nTop 5 Populated Cities in Country: United States", countryCities);

        // 5. New methods for Issue #5
        ArrayList<World.City> districtCities = getTopCitiesInDistrict(con, "California", 5);
        printCities("\nTop 5 Populated Cities in District: California", districtCities);
    }

    /**
     * Analyzes global demographics by retrieving the top N populated cities in the world.
     *
     * @param con The active database connection.
     * @param n   The number of top records to retrieve.
     */
    public static ArrayList<World.City> getTopCitiesInWorld(Connection con, int n) {
        ArrayList<World.City> cities = new ArrayList<>();
        try {
            String query = "SELECT Name, '' AS CountryName, '' AS District, Population FROM city ORDER BY Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                World.City city = new World.City();
                city.setName(rs.getString("Name"));
                city.setCountry(rs.getString("CountryName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Analyzes continental demographics by retrieving the top N populated cities in a continent.
     *
     * @param con       The active database connection.
     * @param continent The target continent to filter by.
     * @param n         The number of top records to retrieve.
     */
    public static ArrayList<World.City> getTopCitiesInContinent(Connection con, String continent, int n) {
        ArrayList<World.City> cities = new ArrayList<>();
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Continent = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, continent);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                World.City city = new World.City();
                city.setName(rs.getString("Name"));
                city.setCountry(rs.getString("CountryName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Analyzes regional demographics by retrieving the top N populated cities in a region.
     *
     * @param con    The active database connection.
     * @param region The target region to filter by.
     * @param n      The number of top records to retrieve.
     */
    public static ArrayList<World.City> getTopCitiesInRegion(Connection con, String region, int n) {
        ArrayList<World.City> cities = new ArrayList<>();
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Region = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, region);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                World.City city = new World.City();
                city.setName(rs.getString("Name"));
                city.setCountry(rs.getString("CountryName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Analyzes national demographics by retrieving the top N populated cities in a country.
     * Addresses Issue #4.
     *
     * @param con     The active database connection.
     * @param country The target country name to filter by.
     * @param n       The number of top records to retrieve.
     */
    public static ArrayList<World.City> getTopCitiesInCountry(Connection con, String country, int n) {
        ArrayList<World.City> cities = new ArrayList<>();
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE country.Name = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, country);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                World.City city = new World.City();
                city.setName(rs.getString("Name"));
                city.setCountry(rs.getString("CountryName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Analyzes local demographics by retrieving the top N populated cities in a district.
     * Addresses Issue #5.
     *
     * @param con      The active database connection.
     * @param district The target district name to filter by.
     * @param n        The number of top records to retrieve.
     */
    public static ArrayList<World.City> getTopCitiesInDistrict(Connection con, String district, int n) {
        ArrayList<World.City> cities = new ArrayList<>();
        try {
            String query = "SELECT city.Name, country.Name AS CountryName, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code " +
                    "WHERE city.District = ? ORDER BY city.Population DESC LIMIT ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, district);
            pstmt.setInt(2, n);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                World.City city = new World.City();
                city.setName(rs.getString("Name"));
                city.setCountry(rs.getString("CountryName"));
                city.setDistrict(rs.getString("District"));
                city.setPopulation(rs.getInt("Population"));
                cities.add(city);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return cities;
    }

    /**
     * Prints a list of cities with a dynamic title and cleanly formatted columns.
     *
     * @param header The title to display above the report.
     * @param cities The list of cities to print.
     */
    public static void printCities(String header, ArrayList<World.City> cities) {
        System.out.println(header);

        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found.");
            return;
        }

        System.out.println(String.format("%-35s %-35s %-35s %-20s", "Name", "Country", "District", "Population"));
        for (World.City city : cities) {
            if (city == null) continue;
            System.out.println(String.format("%-35s %-35s %-35s %-20d",
                    city.getName(),
                    city.getCountry(),
                    city.getDistrict(),
                    city.getPopulation()));
        }
    }
}