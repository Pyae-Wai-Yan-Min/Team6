package com.github.team6;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CountryReport {

    // Entry point called by Main.java
    public static void generateReport(java.sql.Connection con) {

        // Issue #24: All countries in the world by population
        generateWorldReport(con);

        // Issue #25: All countries in a continent by population
        generateContinentReport(con);

        // Issue #26: All countries in a region by population
        generateRegionReport(con);
    }

    // Issue #24: All countries in the world by population
    private static void generateWorldReport(java.sql.Connection con) {
        runReport(
                con,
                "*** All Countries in the World by Population ***",
                null
        );
    }

    // Issue #25: All countries in a continent by population
    private static void generateContinentReport(java.sql.Connection con) {
        runReport(
                con,
                "*** All Countries Grouped By Continent ***",
                "Continent"
        );
    }

    // Issue #26: All countries in a region by population
    private static void generateRegionReport(java.sql.Connection con) {
        runReport(
                con,
                "*** All Countries Grouped By Region ***",
                "Region"
        );
    }

    // Shared method for retrieving and displaying country data
    private static void runReport(
            java.sql.Connection con,
            String reportTitle,
            String groupColumn) {

        String sql =
                "SELECT c.Code, c.Name, c.Continent, c.Region, " +
                        "c.Population, capital.Name AS Capital " +
                        "FROM country c " +
                        "LEFT JOIN city capital ON c.Capital = capital.ID ";

        if (groupColumn == null) {
            sql += "ORDER BY c.Population DESC, c.Name ASC";
        } else if (groupColumn.equals("Continent")) {
            sql += "ORDER BY c.Continent ASC, c.Population DESC, c.Name ASC";
        } else {
            sql += "ORDER BY c.Region ASC, c.Population DESC, c.Name ASC";
        }

        // Distinct visual separation for the title
        System.out.println("\n========================================");
        System.out.println(reportTitle);
        System.out.println("========================================");

        String previousGroup = null;

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            printColumnHeaders();

            while (rs.next()) {

                // Print a heading when a new group begins
                if (groupColumn != null) {
                    String currentGroup = rs.getString(groupColumn);

                    if (previousGroup == null ||
                            !java.util.Objects.equals(currentGroup, previousGroup)) {

                        System.out.println("\n--- " + currentGroup + " ---");
                        printColumnHeaders();
                        previousGroup = currentGroup;
                    }
                }

                System.out.printf(
                        "%-8s %-25s %-20s %-25s %12d %-25s%n",
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getInt("Population"),
                        rs.getString("Capital") == null
                                ? "Unknown"
                                : rs.getString("Capital")
                );
            }

        } catch (SQLException e) {
            System.out.println("Error generating report: " + e.getMessage());
        }
    }

    // Print header
    private static void printColumnHeaders() {
        System.out.printf(
                "%-8s %-25s %-20s %-25s %12s %-25s%n",
                "Code",
                "Name",
                "Continent",
                "Region",
                "Population",
                "Capital"
        );

        System.out.println(
                "---------------------------------------------------------------------------------------------------------------"
        );
    }
}

