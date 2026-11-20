package com.github.team6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Integration tests for the CityPopulationReport class using a REAL MySQL database.
 * Note: Your Docker database must be running for these tests to pass!
 */
public class CityPopulationReportIntegrationTest {

    static java.sql.Connection con;

    @BeforeAll
    static void init() {
        // We now use your team's official Connection class to log in!
        System.out.println("Initializing Integration Tests...");
        con = Connection.connect();
    }

    @Test
    void testGetTopCitiesInWorld() {
        // Fetch the top 5 cities in the world
        ArrayList<World.City> cities = CityPopulationReport.getTopCitiesInWorld(con, 5);

        // Assert that the list is not null and exactly 5 cities were returned
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertEquals(5, cities.size(), "Should return exactly 5 cities.");

        // Print to verify the real data visually
        CityPopulationReport.printCities("--- Integration Test: Top 5 World ---", cities);
    }

    @Test
    void testGetTopCitiesInContinent() {
        // Fetch the top 5 cities in Asia
        ArrayList<World.City> cities = CityPopulationReport.getTopCitiesInContinent(con, "Asia", 5);

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertEquals(5, cities.size(), "Should return exactly 5 cities.");

        CityPopulationReport.printCities("--- Integration Test: Top 5 Asia ---", cities);
    }

    @Test
    void testGetTopCitiesInRegion() {
        // Fetch the top 4 cities in Southeast Asia
        ArrayList<World.City> cities = CityPopulationReport.getTopCitiesInRegion(con, "Southeast Asia", 4);

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertEquals(4, cities.size(), "Should return exactly 4 cities.");

        CityPopulationReport.printCities("--- Integration Test: Top 4 Southeast Asia ---", cities);
    }

    @Test
    void testGetTopCitiesInCountry() {
        // Fetch the top 3 cities in the United States
        ArrayList<World.City> cities = CityPopulationReport.getTopCitiesInCountry(con, "United States", 3);

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertEquals(3, cities.size(), "Should return exactly 3 cities.");

        CityPopulationReport.printCities("--- Integration Test: Top 3 United States ---", cities);
    }

    @Test
    void testGetTopCitiesInDistrict() {
        // Fetch the top 2 cities in California
        ArrayList<World.City> cities = CityPopulationReport.getTopCitiesInDistrict(con, "California", 2);

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertEquals(2, cities.size(), "Should return exactly 2 cities.");

        CityPopulationReport.printCities("--- Integration Test: Top 2 California ---", cities);
    }

    @Test
    void testGenerateReportAndEdgeCases() {
        // 1. Covers the main generateReport(con) method with a live DB connection
        CityPopulationReport.generateReport(con);

        // 2. Covers the null/empty branches inside printCities()
        CityPopulationReport.printCities("Empty List Test", new ArrayList<>());
        CityPopulationReport.printCities("Null List Test", null);

        ArrayList<World.City> listWithNull = new ArrayList<>();
        listWithNull.add(null);
        CityPopulationReport.printCities("Null Element Test", listWithNull);

        // 3. Covers all 5 'catch (Exception e)' blocks by passing a null connection
        CityPopulationReport.getTopCitiesInWorld(null, 5);
        CityPopulationReport.getTopCitiesInContinent(null, "Asia", 5);
        CityPopulationReport.getTopCitiesInRegion(null, "Southeast Asia", 5);
        CityPopulationReport.getTopCitiesInCountry(null, "United States", 5);
        CityPopulationReport.getTopCitiesInDistrict(null, "California", 5);
    }

    @AfterAll
    static void disconnect() {
        // Use your team's official disconnect method to clean up
        Connection.disconnect(con);
    }
}