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
}