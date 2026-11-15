package com.github.team6;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;

/**
 * Unit tests for the PopulationReport class using standard JUnit 5.
 * This mirrors the lab format testing the print and retrieval logic with ArrayLists.
 */
public class PopulationReportTest {

    @Test
    void printContinentReportTestNormal() {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        World.PopulationStats stats1 = new World.PopulationStats();

        stats1.setAreaName("Asia");
        stats1.setTotalPopulation(4000000000L);
        stats1.setCityPopulation(1500000000L);
        stats1.setCityPercentage(37.5);
        stats1.setNonCityPopulation(2500000000L);
        stats1.setNonCityPercentage(62.5);
        statsList.add(stats1);

        // Test the print function with normal population stats data
        PopulationReport.printPopulationStats("POPULATION REPORT BY CONTINENT", "Continent", statsList);
    }

    @Test
    void printPopulationStatsTestEmpty() {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        // Test how the program handles an empty list
        PopulationReport.printPopulationStats("Test Empty Output:", "Continent", statsList);
    }

    @Test
    void printPopulationStatsTestNull() {
        // Test how the program handles a null list completely
        PopulationReport.printPopulationStats("Test Null Output:", "Continent", null);
    }

    @Test
    void printPopulationStatsTestContainsNull() {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        statsList.add(null);
        // Test how the program handles an array list that accidentally has a null object inside
        PopulationReport.printPopulationStats("Test Contains Null Output:", "Continent", statsList);
    }

    @Test
    void printPopulationStatsTestEmptyFields() {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        World.PopulationStats emptyStats = new World.PopulationStats(); // Uninitialized fields
        statsList.add(emptyStats);

        // Test to ensure it doesn't crash when printing empty/default fields
        PopulationReport.printPopulationStats("Test Empty Fields Output:", "Continent", statsList);
    }

    @Test
    void printUniversalStatsTestNormal() {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        World.PopulationStats stats1 = new World.PopulationStats();

        stats1.setAreaName("Shanghai (China)");
        stats1.setTotalPopulation(24870895L);
        stats1.setCityPopulation(24870895L);
        stats1.setCityPercentage(100.00);
        stats1.setNonCityPopulation(0L);
        stats1.setNonCityPercentage(0.00);
        statsList.add(stats1);

        // Test universal printing format for hierarchical / district / city level reports
        PopulationReport.printUniversalStats("POPULATION REPORT BY CITY", "City (Country)", statsList);
    }

    @Test
    void printLanguageStatsTestNormal() {
        ArrayList<World.LanguageStats> statsList = new ArrayList<>();
        World.LanguageStats lang1 = new World.LanguageStats();

        lang1.setLanguage("Chinese");
        lang1.setTotalSpeakers(1200000000L);
        lang1.setWorldPercentage(20.5);
        statsList.add(lang1);

        // Test language report printing format
        PopulationReport.printLanguageStats(statsList);
    }

    @Test
    void printLanguageStatsTestNull() {
        // Test language stats with a null list
        PopulationReport.printLanguageStats(null);
    }

    @Test
    void testGetContinentReportWithNullConnection() {
        // Test data retrieval methods with null connection to ensure graceful exception/fallback handling
        ArrayList<World.PopulationStats> result = PopulationReport.getContinentReport(null);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGetLanguageReportWithNullConnection() {
        ArrayList<World.LanguageStats> result = PopulationReport.getLanguageReport(null);
        org.junit.jupiter.api.Assertions.assertNotNull(result);
        org.junit.jupiter.api.Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void testGenerateReportWithNullConnection() {
        // Test master generateReport execution with a null connection directly (compatible with JUnit 5.1.0)
        PopulationReport.generateReport(null);
    }
}