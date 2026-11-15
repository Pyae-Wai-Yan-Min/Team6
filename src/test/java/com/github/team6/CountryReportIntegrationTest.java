package com.github.team6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Integration tests for the CountryReport class using a REAL MySQL database.
 * Note: Your Docker database must be running for these tests to pass!
 */
public class CountryReportIntegrationTest {

    static java.sql.Connection con;

    @BeforeAll
    static void init() {
        // Use the team's official Connection class to log in
        System.out.println("Initializing Integration Tests...");
        con = Connection.connect();

        assertNotNull(con, "Database connection should not be null.");
    }

    @Test
    void testGetAllCountriesInWorld() {
        // Fetch all countries in the world
        ArrayList<World.Country> countries = CountryReport.getCountries(con, null);

        // Check that the returned list is not null or empty
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The country list should not be empty.");

        // Verify that countries are sorted by population in descending order
        for (int i = 0; i < countries.size() - 1; i++) {
            assertTrue(
                    countries.get(i).getPopulation() >= countries.get(i + 1).getPopulation(),
                    "Countries should be sorted by population from largest to smallest."
            );
        }

        // Print to verify the real data visually
        CountryReport.printCountries(
                "--- Integration Test: All Countries in the World ---",
                null,
                countries
        );
    }

    @Test
    void testGetCountriesGroupedByContinent() {
        // Fetch all countries grouped by continent
        ArrayList<World.Country> countries = CountryReport.getCountries(con, "Continent");

        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The country list should not be empty.");

        // Print to verify the real data visually
        CountryReport.printCountries(
                "--- Integration Test: Countries Grouped By Continent ---",
                "Continent",
                countries
        );
    }

    @Test
    void testGetCountriesGroupedByRegion() {
        // Fetch all countries grouped by region
        ArrayList<World.Country> countries = CountryReport.getCountries(con, "Region");

        assertNotNull(countries, "The returned list of countries should not be null.");
        assertFalse(countries.isEmpty(), "The country list should not be empty.");

        // Print to verify the real data visually
        CountryReport.printCountries(
                "--- Integration Test: Countries Grouped By Region ---",
                "Region",
                countries
        );
    }

    @AfterAll
    static void disconnect() {
        // Use the team's official disconnect method to clean up
        Connection.disconnect(con);
    }
}
