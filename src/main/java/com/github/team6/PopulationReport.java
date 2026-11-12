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
        printLanguageReport(con);
        printWorldReport(con);
        printHierarchicalContinentReport(con);
        printHierarchicalRegionReport(con);
        printHierarchicalCountryReport(con);
        printDistrictReport(con);
        printCityReport(con);
    }

    /*
     * Issue #20 : Gets the Population Report for each Continent.
     */
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

    /*
     *Issue #21 : Gets the Population Report for each Region
     */
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

    /*
     * Issue #22 : Gets the Population Report for each Country
     */
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

    /*
     * Issue #27 : Get population of people who speak Chinese, English, Hindi, Spanish, Arabic
     */
    public static void printLanguageReport(Connection con) {
        String sql = "SELECT " +
                "    cl.Language, " +
                "    SUM(CAST(ROUND(c.Population * (cl.Percentage / 100)) AS UNSIGNED)) AS Total_Speakers, " +
                "    ROUND((SUM(CAST(ROUND(c.Population * (cl.Percentage / 100)) AS UNSIGNED)) / (SELECT SUM(Population) FROM country)) * 100, 2) AS World_Percentage " +
                "FROM countrylanguage cl " +
                "JOIN country c ON cl.CountryCode = c.Code " +
                "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') " +
                "GROUP BY cl.Language " +
                "ORDER BY Total_Speakers DESC";

        System.out.println("\n" + "=".repeat(75));
        System.out.printf(" %-73s %n", "POPULATION REPORT BY LANGUAGE (Chinese, English, Hindi, Spanish, Arabic)");
        System.out.println("=".repeat(75));

        String headerFormat = "| %-20s | %25s | %18s |%n";
        String rowFormat = "| %-20s | %,25d | %17.2f%% |%n";

        System.out.format(headerFormat, "Language", "Total Speakers", "Percentage (%)");
        System.out.println("-".repeat(75));

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String language = rs.getString(1);
                long totalSpeakers = rs.getLong(2);
                double worldPct = rs.getDouble(3);

                System.out.format(rowFormat,
                        truncate(language, 20),
                        totalSpeakers,
                        worldPct
                );
            }

        } catch (SQLException e) {
            System.err.println("Error executing Language Population Report: " + e.getMessage());
        }

        System.out.println("=".repeat(75) + "\n");
    }

    /*
     * Issue #23 : Get population of world, continent, region, country, district, city
     */
    public static void printWorldReport(Connection con) {
        String sql = "SELECT " +
                "    'World' AS Level_Name, " +
                "    SUM(country.Population) AS Total_Pop, " +
                "    COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, " +
                "    ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "    SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "    ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country " +
                "LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode";

        executeAndPrintUniversal(con, "WORLD POPULATION REPORT", "World", sql);
    }

    public static void printHierarchicalContinentReport(Connection con) {
        String sql = "SELECT " +
                "    country.Continent AS Level_Name, " +
                "    SUM(country.Population) AS Total_Pop, " +
                "    COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, " +
                "    ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "    SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "    ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country " +
                "LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode " +
                "GROUP BY country.Continent " +
                "ORDER BY Total_Pop DESC";

        executeAndPrintUniversal(con, "HIERARCHICAL POPULATION REPORT BY CONTINENT", "Continent", sql);
    }

    public static void printHierarchicalRegionReport(Connection con) {
        String sql = "SELECT " +
                "    country.Region AS Level_Name, " +
                "    SUM(country.Population) AS Total_Pop, " +
                "    COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, " +
                "    ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "    SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "    ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country " +
                "LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode " +
                "GROUP BY country.Region " +
                "ORDER BY Total_Pop DESC";

        executeAndPrintUniversal(con, "HIERARCHICAL POPULATION REPORT BY REGION", "Region", sql);
    }

    public static void printHierarchicalCountryReport(Connection con) {
        String sql = "SELECT " +
                "    country.Name AS Level_Name, " +
                "    country.Population AS Total_Pop, " +
                "    COALESCE(city_agg.City_Pop, 0) AS City_Pop, " +
                "    CASE WHEN country.Population = 0 THEN 0 ELSE ROUND((COALESCE(city_agg.City_Pop, 0) / country.Population) * 100, 2) END AS City_Pct, " +
                "    country.Population - COALESCE(city_agg.City_Pop, 0) AS Non_City_Pop, " +
                "    CASE WHEN country.Population = 0 THEN 0 ELSE ROUND(((country.Population - COALESCE(city_agg.City_Pop, 0)) / country.Population) * 100, 2) END AS Non_City_Pct " +
                "FROM country " +
                "LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode " +
                "ORDER BY Total_Pop DESC";

        executeAndPrintUniversal(con, "HIERARCHICAL POPULATION REPORT BY COUNTRY", "Country", sql);
    }

    public static void printDistrictReport(Connection con) {
        String sql = "SELECT " +
                "    CONCAT(city.District, ' (', country.Name, ')') AS Level_Name, " +
                "    SUM(city.Population) AS Total_Pop, " +
                "    SUM(city.Population) AS City_Pop, " +
                "    100.00 AS City_Pct, " +
                "    0 AS Non_City_Pop, " +
                "    0.00 AS Non_City_Pct " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "GROUP BY city.District, country.Name " +
                "ORDER BY Total_Pop DESC";

        executeAndPrintUniversal(con, "POPULATION REPORT BY DISTRICT", "District (Country)", sql);
    }

    public static void printCityReport(Connection con) {
        String sql = "SELECT " +
                "    CONCAT(city.Name, ' (', country.Name, ')') AS Level_Name, " +
                "    city.Population AS Total_Pop, " +
                "    city.Population AS City_Pop, " +
                "    100.00 AS City_Pct, " +
                "    0 AS Non_City_Pop, " +
                "    0.00 AS Non_City_Pct " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY Total_Pop DESC";

        executeAndPrintUniversal(con, "POPULATION REPORT BY CITY", "City (Country)", sql);
    }

    /*
     * Helper method for handling Continent, Region, Country reports
     */
    private static void executeAndPrintReport(Connection con, String title, String nameHeader, String query) {
        System.out.println("\n" + "=".repeat(110));
        System.out.printf(" %-108s %n", title);
        System.out.println("=".repeat(110));

        String headerFormat = "| %-30s | %15s | %15s | %10s | %15s | %10s |%n";
        String rowFormat = "| %-30s | %,15d | %,15d | %10s | %,15d | %10s |%n";

        System.out.format(headerFormat, nameHeader, "Total Population", "City Population", "City Population (%)", "Non-City Population", "Non-CityPopulation(%)");
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


    /*
     * Helper method for handling broader hierarchical data reports (World, Continent, Region, Country, District, City)
     */
    private static void executeAndPrintUniversal(Connection con, String title, String nameHeader, String query) {
        System.out.println("\n" + "=".repeat(110));
        System.out.printf(" %-108s %n", title);
        System.out.println("=".repeat(110));

        String headerFormat = "| %-35s | %15s | %15s | %10s | %15s | %10s |%n";
        String rowFormat = "| %-35s | %,15d | %,15d | %9.2f%% | %,15d | %9.2f%% |%n";

        System.out.format(headerFormat, nameHeader, "Total Population", "City Pop", "City (%)", "Non-City Pop", "Non-City (%)");
        System.out.println("-".repeat(110));

        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString(1);
                long totalPop = rs.getLong(2);
                long cityPop = rs.getLong(3);
                double cityPct = rs.getDouble(4);
                long nonCityPop = rs.getLong(5);
                double nonCityPct = rs.getDouble(6);

                System.out.format(rowFormat,
                        truncate(name, 35),
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