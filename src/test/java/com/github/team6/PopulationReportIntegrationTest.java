package com.github.team6;

import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PopulationReportIntegrationTest {

    @Test
    void testGetContinentReportIntegration() {
        Connection con = com.github.team6.Connection.connect();
        if (con != null) {
            try {
                ArrayList<World.PopulationStats> report = PopulationReport.getContinentReport(con);
                assertNotNull(report, "Continent report list should not be null.");
                assertFalse(report.isEmpty(), "Continent report should retrieve records from the live database.");
            } finally {
                com.github.team6.Connection.disconnect(con);
            }
        }
    }

    @Test
    void testGetLanguageReportIntegration() {
        Connection con = com.github.team6.Connection.connect();
        if (con != null) {
            try {
                ArrayList<World.LanguageStats> report = PopulationReport.getLanguageReport(con);
                assertNotNull(report, "Language report list should not be null.");
                assertFalse(report.isEmpty(), "Language report should retrieve speaker metrics from the live database.");
            } finally {
                com.github.team6.Connection.disconnect(con);
            }
        }
    }

    @Test
    void testGenerateReportIntegration() {
        Connection con = com.github.team6.Connection.connect();
        if (con != null) {
            try {
                // Directly execute the full reporting suite against the live database
                PopulationReport.generateReport(con);
            } finally {
                com.github.team6.Connection.disconnect(con);
            }
        }
    }

    @Test
    void testPrintEdgeCasesAndSqlExceptions() throws Exception {
        // 1. Covers the 'statsList != null' false branches across all three print methods
        PopulationReport.printPopulationStats("NULL POPULATION STATS", "Area", null);
        PopulationReport.printUniversalStats("NULL UNIVERSAL STATS", "Area", null);
        PopulationReport.printLanguageStats(null);

        // 2. Covers truncate() null branch and over-maxLength (> 30 chars) branch
        ArrayList<World.PopulationStats> edgeStats = new ArrayList<>();
        World.PopulationStats nullNameStat = new World.PopulationStats();
        nullNameStat.setAreaName(null);
        edgeStats.add(nullNameStat);

        World.PopulationStats longNameStat = new World.PopulationStats();
        longNameStat.setAreaName("An Extremely Long Area Name That Exceeds Thirty Characters");
        edgeStats.add(longNameStat);

        PopulationReport.printPopulationStats("EDGE CASE STATS", "Area", edgeStats);

        // 3. Covers both 'catch (SQLException e)' blocks using a closed connection
        Connection closedCon = com.github.team6.Connection.connect();
        if (closedCon != null) {
            closedCon.close();

            ArrayList<World.PopulationStats> emptyPop = PopulationReport.getContinentReport(closedCon);
            assertNotNull(emptyPop);
            assertTrue(emptyPop.isEmpty());

            ArrayList<World.LanguageStats> emptyLang = PopulationReport.getLanguageReport(closedCon);
            assertNotNull(emptyLang);
            assertTrue(emptyLang.isEmpty());
        }
    }
}