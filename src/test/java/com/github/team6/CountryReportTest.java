package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Unit tests for the CountryReport class using standard JUnit 5.
 * This mirrors the lab format testing the print logic with ArrayLists.
 */
public class CountryReportTest {

    @Test
    void printCountriesTestNormal() {
        ArrayList<World.Country> countries = new ArrayList<>();

        World.Country country1 = new World.Country();

        // Using setters from our World.java model
        country1.setCode("USA");
        country1.setName("United States");
        country1.setContinent("North America");
        country1.setRegion("North America");
        country1.setPopulation(331000000);
        country1.setCapital("Washington");

        countries.add(country1);

        World.Country country2 = new World.Country();

        country2.setCode("JPN");
        country2.setName("Japan");
        country2.setContinent("Asia");
        country2.setRegion("Eastern Asia");
        country2.setPopulation(125000000);
        country2.setCapital("Tokyo");

        countries.add(country2);

        // Test the print function with normal data
        CountryReport.printCountries("Test Normal Output:", null, countries);
    }

    @Test
    void printCountriesTestEmpty() {
        ArrayList<World.Country> countries = new ArrayList<>();

        // Test how the program handles an empty list
        CountryReport.printCountries("Test Empty Output:", null, countries);
    }

    @Test
    void printCountriesTestNull() {
        // Test how the program handles a null list completely
        CountryReport.printCountries("Test Null Output:", null, null);
    }

    @Test
    void printCountriesTestEmptyFields() {
        ArrayList<World.Country> countries = new ArrayList<>();

        World.Country emptyCountry = new World.Country();
        countries.add(emptyCountry);

        // Test to ensure it doesn't crash when printing empty string fields
        CountryReport.printCountries("Test Empty Fields Output:", null, countries);
    }

    @Test
    void printCountriesTestGroupedByContinent() {
        ArrayList<World.Country> countries = new ArrayList<>();

        World.Country country1 = new World.Country();
        country1.setCode("CHN");
        country1.setName("China");
        country1.setContinent("Asia");
        country1.setRegion("Eastern Asia");
        country1.setPopulation(1412000000);
        country1.setCapital("Beijing");
        countries.add(country1);

        World.Country country2 = new World.Country();
        country2.setCode("JPN");
        country2.setName("Japan");
        country2.setContinent("Asia");
        country2.setRegion("Eastern Asia");
        country2.setPopulation(125000000);
        country2.setCapital("Tokyo");
        countries.add(country2);

        World.Country country3 = new World.Country();
        country3.setCode("FRA");
        country3.setName("France");
        country3.setContinent("Europe");
        country3.setRegion("Western Europe");
        country3.setPopulation(68000000);
        country3.setCapital("Paris");
        countries.add(country3);

        // Test the print function with countries grouped by continent
        CountryReport.printCountries(
                "Test Grouped By Continent Output:", "Continent", countries);
    }

    @Test
    void printCountriesTestGroupedByRegion() {
        ArrayList<World.Country> countries = new ArrayList<>();

        World.Country country1 = new World.Country();
        country1.setCode("CHN");
        country1.setName("China");
        country1.setContinent("Asia");
        country1.setRegion("Eastern Asia");
        country1.setPopulation(1412000000);
        country1.setCapital("Beijing");
        countries.add(country1);

        World.Country country2 = new World.Country();
        country2.setCode("JPN");
        country2.setName("Japan");
        country2.setContinent("Asia");
        country2.setRegion("Eastern Asia");
        country2.setPopulation(125000000);
        country2.setCapital("Tokyo");
        countries.add(country2);

        World.Country country3 = new World.Country();
        country3.setCode("IND");
        country3.setName("India");
        country3.setContinent("Asia");
        country3.setRegion("Southern and Central Asia");
        country3.setPopulation(1380000000);
        country3.setCapital("New Delhi");
        countries.add(country3);

        // Test the print function with countries grouped by region
        CountryReport.printCountries(
                "Test Grouped By Region Output:", "Region", countries);
    }
}

