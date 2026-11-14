package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Unit tests for the CityPopulationReport class using standard JUnit 5.
 * This mirrors the lab format testing the print logic with ArrayLists.
 */
public class CityPopulationReportTest {

    @Test
    void printCitiesTestNormal() {
        ArrayList<World.City> cities = new ArrayList<>();
        World.City city1 = new World.City();

        // Using setters from our new World.java model
        city1.setName("Los Angeles");
        city1.setCountry("United States");
        city1.setDistrict("California");
        city1.setPopulation(3990456);
        cities.add(city1);

        World.City city2 = new World.City();
        city2.setName("San Diego");
        city2.setCountry("United States");
        city2.setDistrict("California");
        city2.setPopulation(1394928);
        cities.add(city2);

        // Test the print function with normal data
        CityPopulationReport.printCities("Test Normal Output:", cities);
    }

    @Test
    void printCitiesTestEmpty() {
        ArrayList<World.City> cities = new ArrayList<>();
        // Test how the program handles an empty list
        CityPopulationReport.printCities("Test Empty Output:", cities);
    }

    @Test
    void printCitiesTestNull() {
        // Test how the program handles a null list completely
        CityPopulationReport.printCities("Test Null Output:", null);
    }

    @Test
    void printCitiesTestContainsNull() {
        ArrayList<World.City> cities = new ArrayList<>();
        cities.add(null);
        // Test how the program handles an array list that accidentally has a null object inside
        CityPopulationReport.printCities("Test Contains Null Output:", cities);
    }

    @Test
    void printCitiesTestEmptyFields() {
        ArrayList<World.City> cities = new ArrayList<>();
        World.City emptyCity = new World.City(); // We didn't set any names or populations!
        cities.add(emptyCity);

        // Test to ensure it doesn't crash when printing empty string fields
        CityPopulationReport.printCities("Test Empty Fields Output:", cities);
    }
}