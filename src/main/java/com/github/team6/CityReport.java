package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CityReport {

    /**
     * Represents a City with Name, Country, District, and Population.
     */
    public static class City {
        public String name;
        public String country;
        public String district;
        public int population;
    }

    /**
     * Generates all required City Reports in one clean execution.
     * @param con The database connection
     */
    public static void generateReport(Connection con) {
        System.out.println("--- Generating City Reports ---\n");

        // Issue #10: All cities in the world by population
        ArrayList<City> worldCities = getAllCitiesInWorld(con);
        printCities("*** All Cities in the World by Population ***", worldCities);

        // Issue #9: All cities in a continent by population
        ArrayList<City> europeCities = getAllCitiesInContinent(con, "Europe");
        printCities("*** All Cities in Europe by Population ***", europeCities);

        // Issue #7: All cities in a country by population
        ArrayList<City> japanCities = getAllCitiesInCountry(con, "Japan");
        printCities("*** All Cities in Japan by Population ***", japanCities);

        // Issue #8: All cities in a region by population
        ArrayList<City> regionCities = getAllCitiesInRegion(con, "Caribbean");
        printCities("*** All Cities in the Caribbean Region by Population ***", regionCities);

        // Issue #6: All cities in a district by population
        ArrayList<City> districtCities = getAllCitiesInDistrict(con, "Maharashtra");
        printCities("*** All Cities in the Maharashtra District by Population ***", districtCities);
    }

    /**
     * Issue #10: Gets all cities in the world ordered by population descending.
     */
    public static ArrayList<City> getAllCitiesInWorld(Connection con) {
        try {
            Statement stmt = con.createStatement();
            String strSelect =
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "ORDER BY city.Population DESC";

            ResultSet rset = stmt.executeQuery(strSelect);
            ArrayList<City> cities = new ArrayList<>();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details for the world");
            return null;
        }
    }

    /**
     * Issue #9: Gets all cities in a given continent ordered by population descending.
     */
    public static ArrayList<City> getAllCitiesInContinent(Connection con, String continent) {
        try {
            String strSelect =
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Continent = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, continent);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<City> cities = new ArrayList<>();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details for continent: " + continent);
            return null;
        }
    }

    /**
     * Issue #7: Gets all cities in a given country ordered by population descending.
     */
    public static ArrayList<City> getAllCitiesInCountry(Connection con, String countryName) {
        try {
            String strSelect =
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Name = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, countryName);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<City> cities = new ArrayList<>();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details for country: " + countryName);
            return null;
        }
    }

    /**
     * Issue #8: Gets all cities in a given region ordered by population descending.
     */
    public static ArrayList<City> getAllCitiesInRegion(Connection con, String regionName) {
        try {
            String strSelect =
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE country.Region = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, regionName);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<City> cities = new ArrayList<>();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details for region: " + regionName);
            return null;
        }
    }

    /**
     * Issue #6: Gets all cities in a given district ordered by population descending.
     */
    public static ArrayList<City> getAllCitiesInDistrict(Connection con, String districtName) {
        try {
            String strSelect =
                    "SELECT city.Name, country.Name AS Country, city.District, city.Population " +
                            "FROM city " +
                            "JOIN country ON city.CountryCode = country.Code " +
                            "WHERE city.District = ? " +
                            "ORDER BY city.Population DESC";

            PreparedStatement pstmt = con.prepareStatement(strSelect);
            pstmt.setString(1, districtName);
            ResultSet rset = pstmt.executeQuery();

            ArrayList<City> cities = new ArrayList<>();

            while (rset.next()) {
                City city = new City();
                city.name = rset.getString("Name");
                city.country = rset.getString("Country");
                city.district = rset.getString("District");
                city.population = rset.getInt("Population");
                cities.add(city);
            }
            return cities;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details for district: " + districtName);
            return null;
        }
    }


    /**
     * Prints a list of cities with a dynamic title and cleanly formatted columns.
     *
     * @param title  The title to display above the report.
     * @param cities The list of cities to print.
     */
    public static void printCities(String title, ArrayList<City> cities) {
        if (cities == null || cities.isEmpty()) {
            System.out.println("No cities found for this report.");
            return;
        }

        // Distinct visual separation for the title
        System.out.println("\n===========================================================================================");
        System.out.println("   " + title);
        System.out.println("===========================================================================================");

        // Print header
        System.out.println(String.format("%-35s %-25s %-25s %-15s", "Name", "Country", "District", "Population"));
        System.out.println("-------------------------------------------------------------------------------------------");

        // Print city rows
        for (City city : cities) {
            if (city == null) continue;

            // Replaces null fields with a dash to prevent formatting crashes
            String nameString = (city.name != null) ? city.name : "-";
            String countryString = (city.country != null) ? city.country : "-";
            String districtString = (city.district != null && !city.district.isEmpty()) ? city.district : "-";

            // Uses %,d to automatically add commas to the population integer
            System.out.println(String.format("%-35s %-25s %-25s %,15d",
                    nameString, countryString, districtString, city.population));
        }
        System.out.println(); // Adds an empty line after the report finishes
    }
}