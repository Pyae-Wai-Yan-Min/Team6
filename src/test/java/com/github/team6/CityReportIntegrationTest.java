package com.github.team6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Integration tests for the CityReport class using a REAL MySQL database.
 * Note: Your Docker database must be running for these tests to pass!
 */
public class CityReportIntegrationTest {

    static java.sql.Connection con;

    @BeforeAll
    static void init() {
        // Uses your team's official Connection class to connect to the database
        System.out.println("Initializing City Report Integration Tests...");
        con = Connection.connect();
    }

    @Test
    void testGetAllCitiesInWorld() {
        // Fetch real data using your exact method name
        ArrayList<World.City> cities = CityReport.getAllCitiesInWorld(con);

        // Assert that the list successfully pulled data from the database
        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The list of world cities should not be empty.");

        // Print to visually verify the live data
        CityReport.printCities("--- Integration Test: All Cities in the World ---", cities);
    }

    @Test
    void testGetAllCitiesInContinent() {
        ArrayList<World.City> cities = CityReport.getAllCitiesInContinent(con, "Europe");

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The list of European cities should not be empty.");

        CityReport.printCities("--- Integration Test: All Cities in Europe ---", cities);
    }

    @Test
    void testGetAllCitiesInCountry() {
        ArrayList<World.City> cities = CityReport.getAllCitiesInCountry(con, "Japan");

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The list of Japanese cities should not be empty.");

        CityReport.printCities("--- Integration Test: All Cities in Japan ---", cities);
    }

    @Test
    void testGetAllCitiesInRegion() {
        ArrayList<World.City> cities = CityReport.getAllCitiesInRegion(con, "Caribbean");

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The list of Caribbean cities should not be empty.");

        CityReport.printCities("--- Integration Test: All Cities in the Caribbean ---", cities);
    }

    @Test
    void testGetAllCitiesInDistrict() {
        ArrayList<World.City> cities = CityReport.getAllCitiesInDistrict(con, "Maharashtra");

        assertNotNull(cities, "The returned list of cities should not be null.");
        assertFalse(cities.isEmpty(), "The list of Maharashtra cities should not be empty.");

        CityReport.printCities("--- Integration Test: All Cities in Maharashtra ---", cities);
    }

    @AfterAll
    static void disconnect() {
        // Cleanly disconnects using your team's utility method
        Connection.disconnect(con);
    }
}