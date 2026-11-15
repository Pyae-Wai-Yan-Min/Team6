package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Unit tests for the CountryPopulationReport class using standard JUnit 5.
 * Tests the printing and reporting logic for Issues #17, #18, and #19.
 */
public class CountryPopulationReportTest {

    @Test
    void printCountriesTestNormal() {
        ArrayList<World.Country> countries = new ArrayList<>();
        World.Country country1 = new World.Country();

        country1.setCode("CHN");
        country1.setName("China");
        country1.setContinent("Asia");
        country1.setRegion("Eastern Asia");
        country1.setPopulation(1277558000);
        country1.setCapital("Peking");
        countries.add(country1);

        World.Country country2 = new World.Country();
        country2.setCode("IND");
        country2.setName("India");
        country2.setContinent("Asia");
        country2.setRegion("Southern and Central Asia");
        country2.setPopulation(1013662000);
        country2.setCapital("New Delhi");
        countries.add(country2);

        // Test the print function with normal data
        CountryPopulationReport.printCountries("Top 2 Populated Countries Test", countries);
    }

    @Test
    void printCountriesTestEmpty() {
        ArrayList<World.Country> countries = new ArrayList<>();
        // Test how the program handles an empty list
        CountryPopulationReport.printCountries("Test Empty Output:", countries);
    }

    @Test
    void printCountriesTestNull() {
        // Test how the program handles a null list completely
        CountryPopulationReport.printCountries("Test Null Output:", null);
    }

    @Test
    void printCountriesTestContainsNull() {
        ArrayList<World.Country> countries = new ArrayList<>();
        countries.add(null);
        // Test how the program handles an array list that has a null object inside
        CountryPopulationReport.printCountries("Test Contains Null Output:", countries);
    }

    @Test
    void printCountriesTestEmptyFields() {
        ArrayList<World.Country> countries = new ArrayList<>();
        World.Country emptyCountry = new World.Country(); // Uninitialized fields
        countries.add(emptyCountry);

        // Test to ensure it handles missing or null string/number fields safely
        CountryPopulationReport.printCountries("Test Empty Fields Output:", countries);
    }
}