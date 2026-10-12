package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PopulationReport {

    public static void generateReport(Connection con) {
        printContinentReport(con);
        printRegionReport(con);
        printCountryReport(con);
    }

    // Prints the Population Report for all Continents.
    public static void printContinentReport(Connection con) {
        String sql = "SELECT " +
                "    c.Continent AS Continent_Name, " +
                "    c.Total_Population, " +
                "    c.City_Population, " +
                "    CONCAT(ROUND((c.City_Population / c.Total_Population) * 100, 2), '%') AS City_Population_Pct, " +
                "    c.Non_City_Population, " +
                "    CONCAT(ROUND((c.Non_City_Population / c.Total_Population) * 100, 2), '%') AS Non_City_Population_Pct " +
                "FROM (" +
                "    SELECT " +
                "        country.Continent, " +
                "        SUM(country.Population) AS Total_Population, " +
                "        COALESCE(SUM(city_agg.City_Pop), 0) AS City_Population, " +
                "        SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Population " +
                "    FROM country " +
                "    LEFT JOIN (" +
                "        SELECT CountryCode, SUM(Population) AS City_Pop " +
                "        FROM city " +
                "        GROUP BY CountryCode " +
                "    ) city_agg ON country.Code = city_agg.CountryCode " +
                "    GROUP BY country.Continent " +
                ") c " +
                "ORDER BY c.Total_Population DESC";

        executeAndPrintReport(con, "POPULATION REPORT BY CONTINENT", "Continent", sql);
    }

    // Prints the Population Report for all Region.
    public static void printRegionReport(Connection con) {
        String sql = "SELECT " +
                "    r.Region AS Region_Name, " +
                "    r.Total_Population, " +
                "    r.City_Population, " +
                "    CONCAT(ROUND((r.City_Population / r.Total_Population) * 100, 2), '%') AS City_Population_Pct, " +
                "    r.Non_City_Population, " +
                "    CONCAT(ROUND((r.Non_City_Population / r.Total_Population) * 100, 2), '%') AS Non_City_Population_Pct " +
                "FROM (" +
                "    SELECT " +
                "        country.Region, " +
                "        SUM(country.Population) AS Total_Population, " +
                "        COALESCE(SUM(city_agg.City_Pop), 0) AS City_Population, " +
                "        SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Population " +
                "    FROM country " +
                "    LEFT JOIN (" +
                "        SELECT CountryCode, SUM(Population) AS City_Pop " +
                "        FROM city " +
                "        GROUP BY CountryCode " +
                "    ) city_agg ON country.Code = city_agg.CountryCode " +
                "    GROUP BY country.Region " +
                ") r " +
                "WHERE r.Total_Population > 0 " +
                "ORDER BY r.Total_Population DESC";

        executeAndPrintReport(con, "POPULATION REPORT BY REGION", "Region", sql);
    }

    // Prints the Population Report for all Country
    public static void printCountryReport(Connection con) {
        String sql = "SELECT " +
                "    co.Country_Name, " +
                "    co.Total_Population, " +
                "    co.City_Population, " +
                "    CASE WHEN co.Total_Population = 0 THEN '0.00%' ELSE CONCAT(ROUND((co.City_Population / co.Total_Population) * 100, 2), '%') END AS City_Population_Pct, " +
                "    co.Non_City_Population, " +
                "    CASE WHEN co.Total_Population = 0 THEN '0.00%' ELSE CONCAT(ROUND((co.Non_City_Population / co.Total_Population) * 100, 2), '%') END AS Non_City_Population_Pct " +
                "FROM (" +
                "    SELECT " +
                "        country.Name AS Country_Name, " +
                "        country.Population AS Total_Population, " +
                "        COALESCE(SUM(city.Population), 0) AS City_Population, " +
                "        country.Population - COALESCE(SUM(city.Population), 0) AS Non_City_Population " +
                "    FROM country " +
                "    LEFT JOIN city ON country.Code = city.CountryCode " +
                "    GROUP BY country.Code, country.Name, country.Population " +
                ") co " +
                "ORDER BY co.Total_Population DESC";

        executeAndPrintReport(con, "POPULATION REPORT BY COUNTRY", "Country", sql);
    }

     // Method to execute queries and display tabular outputs.
    private static void executeAndPrintReport(Connection con, String title, String nameHeader, String query) {
        System.out.println("\n" + "=".repeat(110));
        System.out.printf(" %-108s %n", title);
        System.out.println("=".repeat(110));

        String headerFormat = "| %-30s | %15s | %15s | %10s | %15s | %10s |%n";
        String rowFormat    = "| %-30s | %,15d | %,15d | %10s | %,15d | %10s |%n";

        System.out.format(headerFormat, nameHeader, "Total Pop", "City Pop", "City %", "Non-City Pop", "Non-City %");
        System.out.println("-".repeat(110));

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString(1);
                long totalPop = rs.getLong(2);
                long cityPop = rs.getLong(3);
                String cityPct = rs.getString(4);
                long nonCityPop = rs.getLong(5);
                String nonCityPct = rs.getString(6);

                System.out.format(rowFormat,
                        truncate(name, 30),
                        totalPop,
                        cityPop,
                        cityPct,
                        nonCityPop,
                        nonCityPct
                );
            }

        } catch (SQLException e) {
            System.err.println("Error executing " + title + ": " + e.getMessage());
        }

        System.out.println("=".repeat(110) + "\n");
    }

    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}