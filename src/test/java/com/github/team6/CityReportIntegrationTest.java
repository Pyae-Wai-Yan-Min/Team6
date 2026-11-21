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

    @Test
    void testGenerateReportAndEdgeCases() {
        // 1. Covers the main generateReport(con) method with a live DB connection
        CityReport.generateReport(con);

        // 2. Covers the null, empty, null-element, and empty-field branches inside printCities()
        CityReport.printCities("Empty List Test", new ArrayList<>());
        CityReport.printCities("Null List Test", null);

        ArrayList<World.City> listWithEdgeCases = new ArrayList<>();
        listWithEdgeCases.add(null);

        World.City nullFieldsCity = new World.City();
        listWithEdgeCases.add(nullFieldsCity);

        World.City emptyDistrictCity = new World.City();
        emptyDistrictCity.setName("Test City");
        emptyDistrictCity.setCountry("Test Country");
        emptyDistrictCity.setDistrict("");
        emptyDistrictCity.setPopulation(1000);
        listWithEdgeCases.add(emptyDistrictCity);

        CityReport.printCities("Edge Case Fields Test", listWithEdgeCases);

        // 3. Covers all 5 'catch (Exception e)' blocks by passing a null connection
        assertNull(CityReport.getAllCitiesInWorld(null));
        assertNull(CityReport.getAllCitiesInContinent(null, "Europe"));
        assertNull(CityReport.getAllCitiesInCountry(null, "Japan"));
        assertNull(CityReport.getAllCitiesInRegion(null, "Caribbean"));
        assertNull(CityReport.getAllCitiesInDistrict(null, "Maharashtra"));
    }

    @AfterAll
    static void disconnect() {
        // Cleanly disconnects using your team's utility method
        Connection.disconnect(con);
    }
}