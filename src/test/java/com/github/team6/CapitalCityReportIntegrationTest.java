package com.github.team6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Integration tests for the CapitalCityReport class using a REAL MySQL database.
 * Note: Your Docker database must be running for these tests to pass!
 */
public class CapitalCityReportIntegrationTest {

    static java.sql.Connection con;

    @BeforeAll
    static void init() {
        // Connect to the real database
        System.out.println("Initializing Integration Tests for Capital City...");
        con = Connection.connect();
    }

    @Test
    void testGetCapitalCitiesInWorld() {
        // Fetch all capital cities in the world
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getAllCapitalCitiesInWorld(con);

        assertNotNull(capitals, "The returned list of capital cities should not be null.");
        assertTrue(capitals.size() > 0, "Should return more than 0 capital cities.");

        CapitalCityReport.printCapitalCities("--- Integration Test: All World Capitals ---", capitals);
    }

    @Test
    void testGetCapitalCitiesInContinent() {
        // Fetch all capital cities in Europe
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getAllCapitalCitiesInContinent(con, "Europe");

        assertNotNull(capitals, "The returned list should not be null.");
        assertTrue(capitals.size() > 0, "Should return capital cities for Europe.");

        CapitalCityReport.printCapitalCities("--- Integration Test: Europe Capitals ---", capitals);
    }

    @Test
    void testGetCapitalCitiesInRegion() {
        // Fetch all capital cities in Western Europe
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getAllCapitalCitiesInRegion(con, "Western Europe");

        assertNotNull(capitals, "The returned list should not be null.");
        assertTrue(capitals.size() > 0, "Should return capital cities for Western Europe.");

        CapitalCityReport.printCapitalCities("--- Integration Test: Western Europe Capitals ---", capitals);
    }

    @Test
    void testGetTopNCapitalCitiesInWorld() {
        // Fetch the top 5 capital cities in the world
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getTopNCapitalCitiesInWorld(con, 5);

        assertNotNull(capitals, "The returned list should not be null.");
        assertEquals(5, capitals.size(), "Should return exactly 5 capital cities.");

        CapitalCityReport.printCapitalCities("--- Integration Test: Top 5 World Capitals ---", capitals);
    }

    @Test
    void testGetTopNCapitalCitiesInContinent() {
        // Fetch the top 3 capital cities in Asia
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getTopNCapitalCitiesInContinent(con, "Asia", 3);

        assertNotNull(capitals, "The returned list should not be null.");
        assertEquals(3, capitals.size(), "Should return exactly 3 capital cities.");

        CapitalCityReport.printCapitalCities("--- Integration Test: Top 3 Asia Capitals ---", capitals);
    }

    @Test
    void testGetTopNCapitalCitiesInRegion() {
        // Fetch the top 2 capital cities in Eastern Europe
        ArrayList<World.CapitalCity> capitals = CapitalCityReport.getTopNCapitalCitiesInRegion(con, "Eastern Europe", 2);

        assertNotNull(capitals, "The returned list should not be null.");
        assertEquals(2, capitals.size(), "Should return exactly 2 capital cities.");

        CapitalCityReport.printCapitalCities("--- Integration Test: Top 2 Eastern Europe Capitals ---", capitals);
    }

    @AfterAll
    static void disconnect() {
        // Close the database connection
        Connection.disconnect(con);
    }
}