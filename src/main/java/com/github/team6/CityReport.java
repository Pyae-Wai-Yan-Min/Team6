package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CityReport {

    /**
     * Generates all required City Reports in one clean execution.
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating City Reports ---\n");

        // Issue #10: All cities in the world by population
        ArrayList<World.City> worldCities = getAllCitiesInWorld(con);
        printCities("*** All Cities in the World by Population ***", worldCities);

        // Issue #9: All cities in a continent by population
        ArrayList<World.City> europeCities = getAllCitiesInContinent(con, "Europe");
        printCities("*** All Cities in Europe by Population ***", europeCities);

        // Issue #7: All cities in a country by population
        ArrayList<World.City> japanCities = getAllCitiesInCountry(con, "Japan");
        printCities("*** All Cities in Japan by Population ***", japanCities);

        // Issue #8: All cities in a region by population
        ArrayList<World.City> regionCities = getAllCitiesInRegion(con, "Caribbean");
        printCities("*** All Cities in the Caribbean Region by Population ***", regionCities);

        // Issue #6: All cities in a district by population
        ArrayList<World.City> districtCities = getAllCitiesInDistrict(con, "Maharashtra");
        printCities("*** All Cities in the Maharashtra District by Population ***", districtCities);
    }

    /**
     * Issue #10: Gets all cities in the world ordered by population descending.
     */
    public static ArrayList<World.City> getAllCitiesInWorld(Connection con) {
        try {
            Statement stmt = con.createStatement();
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code ORDER BY city.Population DESC";
            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<World.City> cities = new ArrayList<>();
            while (rset.next()) {
                World.City city = new World.City();
                city.setName(rset.getString("Name"));
                city.setCountry(rset.getString("Country"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println("Failed to get city details for the world: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #9: Gets all cities in a given continent ordered by population descending.
     */
    public static ArrayList<World.City> getAllCitiesInContinent(Connection con, String continent) {
        try {
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Continent = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, continent);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.City> cities = new ArrayList<>();
            while (rset.next()) {
                World.City city = new World.City();
                city.setName(rset.getString("Name"));
                city.setCountry(rset.getString("Country"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println("Failed to get city details for continent: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #7: Gets all cities in a given country ordered by population descending.
     */
    public static ArrayList<World.City> getAllCitiesInCountry(Connection con, String countryName) {
        try {
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Name = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, countryName);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.City> cities = new ArrayList<>();
            while (rset.next()) {
                World.City city = new World.City();
                city.setName(rset.getString("Name"));
                city.setCountry(rset.getString("Country"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println("Failed to get city details for country: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #8: Gets all cities in a given region ordered by population descending.
     */
    public static ArrayList<World.City> getAllCitiesInRegion(Connection con, String regionName) {
        try {
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code WHERE country.Region = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, regionName);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.City> cities = new ArrayList<>();
            while (rset.next()) {
                World.City city = new World.City();
                city.setName(rset.getString("Name"));
                city.setCountry(rset.getString("Country"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println("Failed to get city details for region: " + e.getMessage());
            return null;
        }
    }

    /**
     * Issue #6: Gets all cities in a given district ordered by population descending.
     */
    public static ArrayList<World.City> getAllCitiesInDistrict(Connection con, String districtName) {
        try {
            String strSelect = "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                    "FROM city JOIN country ON city.CountryCode = country.Code WHERE city.District = ? ORDER BY city.Population DESC";
            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, districtName);
            ResultSet rset = pstmt.executeQuery();
            ArrayList<World.City> cities = new ArrayList<>();
            while (rset.next()) {
                World.City city = new World.City();
                city.setName(rset.getString("Name"));
                city.setCountry(rset.getString("Country"));
                city.setDistrict(rset.getString("District"));
                city.setPopulation(rset.getInt("Population"));
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println("Failed to get city details for district: " + e.getMessage());
            return null;
        }
    }

    /**
     * Prints a list of cities with a dynamic title and cleanly formatted columns.
     *
     * @param title  The title to display above the report.
     * @param cities The list of cities to print.
     */
    public static void printCities(String title, ArrayList<World.City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found for this report.");
            return;
        }
        System.out.println("\n===========================================================================================");
        System.out.println("   " + title);
        System.out.println("===========================================================================================");
        System.out.println(String.format("%-35s %-25s %-25s %-15s", "Name", "Country", "District", "Population"));
        System.out.println("-------------------------------------------------------------------------------------------");

        for (World.City city : cities) {
            if (city == null) continue;
            String nameString = (city.getName() != null) ? city.getName() : "-";
            String countryString = (city.getCountry() != null) ? city.getCountry() : "-";
            String districtString = (city.getDistrict() != null && !city.getDistrict().isEmpty()) ? city.getDistrict() : "-";

            System.out.println(String.format("%-35s %-25s %-25s %,15d",
                    nameString, countryString, districtString, city.getPopulation()));
        }
        System.out.println();
    }
}