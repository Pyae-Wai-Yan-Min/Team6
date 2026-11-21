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

    @Test
    void testGenerateReportAndEdgeCases() throws Exception {
        // 1. Covers the main generateReport(con) method with a live DB connection
        CountryReport.generateReport(con);

        // 2. Covers empty, null, and null-capital branches inside printCountries()
        CountryReport.printCountries("Empty List Test", null, new ArrayList<>());
        CountryReport.printCountries("Null List Test", null, null);

        ArrayList<World.Country> nullCapitalList = new ArrayList<>();
        World.Country countryWithNullCapital = new World.Country();
        countryWithNullCapital.setCode("TST");
        countryWithNullCapital.setName("Testland");
        countryWithNullCapital.setContinent("Europe");
        countryWithNullCapital.setRegion("Western Europe");
        countryWithNullCapital.setPopulation(1000);
        countryWithNullCapital.setCapital(null); // Triggers the 'c.getCapital() == null ? "Unknown"' branch
        nullCapitalList.add(countryWithNullCapital);
        CountryReport.printCountries("Null Capital Test", null, nullCapitalList);

        // 3. Open and immediately close a temporary connection so JDBC throws a genuine SQLException
        java.sql.Connection closedCon = Connection.connect();
        assertNotNull(closedCon);
        closedCon.close();

        ArrayList<World.Country> errorResult = CountryReport.getCountries(closedCon, null);
        assertNotNull(errorResult);
        assertTrue(errorResult.isEmpty());
    }

    @AfterAll
    static void disconnect() {
        // Use the team's official disconnect method to clean up
        Connection.disconnect(con);
    }
}