package com.github.team6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

/**
 * Integration tests for the CountryPopulationReport class using a REAL MySQL database.
 * Covers Issue #17 (World), Issue #18 (Continent), and Issue #19 (Region).
 * Note: Your Docker database must be running for these tests to pass!
 */
public class CountryPopulationReportIntegrationTest {

    static java.sql.Connection con;

    @BeforeAll
    static void init() {
        // Use the team's official Connection class to connect to the database
        System.out.println("Initializing CountryPopulationReport Integration Tests...");
        con = Connection.connect();
        assertNotNull(con, "Database connection should not be null.");
    }

    /**
     * Issue #17: Top N populated countries in the world
     */
    @Test
    void testGetTopCountriesInWorld() {
        // Fetch the top 5 populated countries in the world
        ArrayList<World.Country> countries = CountryPopulationReport.getTopCountriesInWorld(con, 5);

        // Assert that the list is not null and returns exactly 5 countries
        assertNotNull(countries, "The returned list of countries should not be null.");
        assertEquals(5, countries.size(), "Should return exactly 5 countries.");

        // Verify sorting (largest to smallest population) and required columns
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation(),
                "Countries should be sorted by population in descending order.");
        assertNotNull(countries.get(0).getCode(), "Country code should not be null.");
        assertNotNull(countries.get(0).getName(), "Country name should not be null.");
        assertNotNull(countries.get(0).getContinent(), "Continent should not be null.");
        assertNotNull(countries.get(0).getRegion(), "Region should not be null.");

        // Print to verify the real data visually
        CountryPopulationReport.printCountries("--- Integration Test: Top 5 World ---", countries);
    }

    /**
     * Issue #18: Top N populated countries in a continent
     */
    @Test
    void testGetTopCountriesInContinent() {
        // Fetch the top 5 populated countries in Asia
        ArrayList<World.Country> countries = CountryPopulationReport.getTopCountriesInContinent(con, "Asia", 5);

        assertNotNull(countries, "The returned list of countries should not be null.");
        assertEquals(5, countries.size(), "Should return exactly 5 countries.");

        // Verify all returned countries belong to Asia and are sorted descending
        for (World.Country country : countries) {
            assertEquals("Asia", country.getContinent(), "Country continent should be Asia.");
        }
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation(),
                "Countries in continent should be sorted by population descending.");

        CountryPopulationReport.printCountries("--- Integration Test: Top 5 Asia ---", countries);
    }

    /**
     * Issue #19: Top N populated countries in a region
     */
    @Test
    void testGetTopCountriesInRegion() {
        // Fetch the top 4 populated countries in Southeast Asia
        ArrayList<World.Country> countries = CountryPopulationReport.getTopCountriesInRegion(con, "Southeast Asia", 4);

        assertNotNull(countries, "The returned list of countries should not be null.");
        assertEquals(4, countries.size(), "Should return exactly 4 countries.");

        // Verify all returned countries belong to Southeast Asia and are sorted descending
        for (World.Country country : countries) {
            assertEquals("Southeast Asia", country.getRegion(), "Country region should be Southeast Asia.");
        }
        assertTrue(countries.get(0).getPopulation() >= countries.get(1).getPopulation(),
                "Countries in region should be sorted by population descending.");

        CountryPopulationReport.printCountries("--- Integration Test: Top 4 Southeast Asia ---", countries);
    }

    /**
     * Edge case: Non-existent continent returns an empty list cleanly
     */
    @Test
    void testGetTopCountriesInInvalidContinent() {
        ArrayList<World.Country> countries = CountryPopulationReport.getTopCountriesInContinent(con, "Atlantis", 5);

        assertNotNull(countries, "Should return an empty list rather than null for unknown continent.");
        assertTrue(countries.isEmpty(), "List should be empty for a non-existent continent.");
    }

    /**
     * Edge case: Non-existent region returns an empty list cleanly
     */
    @Test
    void testGetTopCountriesInInvalidRegion() {
        ArrayList<World.Country> countries = CountryPopulationReport.getTopCountriesInRegion(con, "UnknownRegion", 5);

        assertNotNull(countries, "Should return an empty list rather than null for unknown region.");
        assertTrue(countries.isEmpty(), "List should be empty for a non-existent region.");
    }

    /**
     * Verifies full report execution against the live database
     */
    @Test
    void testGenerateReport() {
        CountryPopulationReport.generateReport(con);
    }

    /**
     * Covers printCountries() edge cases and all 3 'catch (SQLException e)' blocks
     */
    @Test
    void testPrintEdgeCasesAndSqlExceptions() throws Exception {
        // 1. Cover null, empty, null-element, and null-field branches inside printCountries()
        CountryPopulationReport.printCountries("Empty List Test", new ArrayList<>());
        CountryPopulationReport.printCountries("Null List Test", null);

        ArrayList<World.Country> edgeCaseList = new ArrayList<>();
        edgeCaseList.add(null);
        edgeCaseList.add(new World.Country()); // All fields null (triggers all 5 '? : "-"' ternary fallbacks)
        CountryPopulationReport.printCountries("Edge Cases Test", edgeCaseList);

        // 2. Open and immediately close a temporary connection so JDBC throws a genuine SQLException
        java.sql.Connection closedCon = Connection.connect();
        assertNotNull(closedCon);
        closedCon.close();

        assertNull(CountryPopulationReport.getTopCountriesInWorld(closedCon, 5));
        assertNull(CountryPopulationReport.getTopCountriesInContinent(closedCon, "Asia", 5));
        assertNull(CountryPopulationReport.getTopCountriesInRegion(closedCon, "Southeast Asia", 4));
    }

    @AfterAll
    static void disconnect() {
        // Use the team's official disconnect method to clean up
        Connection.disconnect(con);
    }
}