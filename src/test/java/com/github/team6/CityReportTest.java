package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class CityReportTest {

    @Test
    void printCitiesTestNormal() {
        // Notice it uses World.City now, matching the team's new structure
        ArrayList<World.City> cities = new ArrayList<>();
        World.City city1 = new World.City();

        city1.setName("Yangon");
        city1.setCountry("Myanmar");
        city1.setDistrict("Yangon");
        city1.setPopulation(5000000);
        cities.add(city1);

        World.City city2 = new World.City();
        city2.setName("Mandalay");
        city2.setCountry("Myanmar");
        city2.setDistrict("Mandalay");
        city2.setPopulation(1225714);
        cities.add(city2);

        CityReport.printCities("Test Normal Output:", cities);
    }

    @Test
    void printCitiesTestEmpty() {
        ArrayList<World.City> cities = new ArrayList<>();
        CityReport.printCities("Test Empty Output:", cities);
    }

    @Test
    void printCitiesTestNull() {
        CityReport.printCities("Test Null Output:", null);
    }

    @Test
    void printCitiesTestContainsNull() {
        ArrayList<World.City> cities = new ArrayList<>();
        cities.add(null);
        CityReport.printCities("Test Contains Null Output:", cities);
    }

    @Test
    void printCitiesTestEmptyFields() {
        ArrayList<World.City> cities = new ArrayList<>();
        World.City emptyCity = new World.City();
        cities.add(emptyCity);

        CityReport.printCities("Test Empty Fields Output:", cities);
    }
}