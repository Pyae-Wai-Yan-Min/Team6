package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Unit tests for the CapitalCityReport class using standard JUnit 5.
 * This mirrors the lab format testing the print logic with ArrayLists.
 */
public class CapitalCityReportTest {

    @Test
    void printCapitalCitiesTestNormal() {
        ArrayList<World.CapitalCity> capitals = new ArrayList<>();
        World.CapitalCity city1 = new World.CapitalCity();

        // Using setters from the World.java model
        city1.setName("London");
        city1.setCountry("United Kingdom");
        city1.setPopulation(8982000);
        capitals.add(city1);

        World.CapitalCity city2 = new World.CapitalCity();
        city2.setName("Paris");
        city2.setCountry("France");
        city2.setPopulation(2148000);
        capitals.add(city2);

        // Test the print function with normal data
        CapitalCityReport.printCapitalCities("Test Normal Output:", capitals);
    }

    @Test
    void printCapitalCitiesTestEmpty() {
        ArrayList<World.CapitalCity> capitals = new ArrayList<>();
        // Test how the program handles an empty list
        CapitalCityReport.printCapitalCities("Test Empty Output:", capitals);
    }

    @Test
    void printCapitalCitiesTestNull() {
        // Test how the program handles a null list completely
        CapitalCityReport.printCapitalCities("Test Null Output:", null);
    }

    @Test
    void printCapitalCitiesTestContainsNull() {
        ArrayList<World.CapitalCity> capitals = new ArrayList<>();
        capitals.add(null);
        // Test how the program handles an array list that accidentally has a null object inside
        CapitalCityReport.printCapitalCities("Test Contains Null Output:", capitals);
    }

    @Test
    void printCapitalCitiesTestEmptyFields() {
        ArrayList<World.CapitalCity> capitals = new ArrayList<>();
        World.CapitalCity emptyCity = new World.CapitalCity(); // We didn't set any names or populations!
        capitals.add(emptyCity);

        // Test to ensure it doesn't crash when printing empty string fields
        CapitalCityReport.printCapitalCities("Test Empty Fields Output:", capitals);
    }
}