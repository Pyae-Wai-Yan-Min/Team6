package com.github.team6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PopulationReport {

    /**
     * Master method invoked directly from Main.java to execute all population reports.
     *
     * @param con The active database connection.
     */
    public static void generateReport(Connection con) {
        printPopulationStats("POPULATION REPORT BY CONTINENT", "Continent", getContinentReport(con));
        printPopulationStats("POPULATION REPORT BY REGION", "Region", getRegionReport(con));
        printPopulationStats("POPULATION REPORT BY COUNTRY", "Country", getCountryReport(con));

        printLanguageStats(getLanguageReport(con));

        printUniversalStats("WORLD POPULATION REPORT", "World", getWorldReport(con));
        printUniversalStats("HIERARCHICAL POPULATION REPORT BY CONTINENT", "Continent", getHierarchicalContinentReport(con));
        printUniversalStats("HIERARCHICAL POPULATION REPORT BY REGION", "Region", getHierarchicalRegionReport(con));
        printUniversalStats("HIERARCHICAL POPULATION REPORT BY COUNTRY", "Country", getHierarchicalCountryReport(con));
        printUniversalStats("POPULATION REPORT BY DISTRICT", "District (Country)", getDistrictReport(con));
        printUniversalStats("POPULATION REPORT BY CITY", "City (Country)", getCityReport(con));
    }

    /**
     * Issue #20 : Gets the Population Report for each Continent.
     *
     * @param con The active database connection.
     */
    public static ArrayList<World.PopulationStats> getContinentReport(Connection con) {
        String sql = "SELECT c.Continent AS Level_Name, c.Total_Population, c.City_Population, " +
                "ROUND((c.City_Population / c.Total_Population) * 100, 2) AS City_Pct, " +
                "c.Non_City_Population, ROUND((c.Non_City_Population / c.Total_Population) * 100, 2) AS Non_City_Pct " +
                "FROM (SELECT country.Continent, SUM(country.Population) AS Total_Population, " +
                "COALESCE(SUM(city_agg.City_Pop), 0) AS City_Population, " +
                "SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Population " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode GROUP BY country.Continent) c ORDER BY c.Total_Population DESC";
        return fetchPopulationStats(con, sql);
    }

    /**
     * Issue #21 : Gets the Population Report for each Region
     *
     * @param con The active database connection.
     */
    public static ArrayList<World.PopulationStats> getRegionReport(Connection con) {
        String sql = "SELECT r.Region AS Level_Name, r.Total_Population, r.City_Population, " +
                "ROUND((r.City_Population / r.Total_Population) * 100, 2) AS City_Pct, " +
                "r.Non_City_Population, ROUND((r.Non_City_Population / r.Total_Population) * 100, 2) AS Non_City_Pct " +
                "FROM (SELECT country.Region, SUM(country.Population) AS Total_Population, " +
                "COALESCE(SUM(city_agg.City_Pop), 0) AS City_Population, " +
                "SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Population " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode GROUP BY country.Region) r WHERE r.Total_Population > 0 ORDER BY r.Total_Population DESC";
        return fetchPopulationStats(con, sql);
    }

    /**
     * Issue #22 : Gets the Population Report for each Country
     *
     * @param con The active database connection.
     */
    public static ArrayList<World.PopulationStats> getCountryReport(Connection con) {
        String sql = "SELECT co.Country_Name AS Level_Name, co.Total_Population, co.City_Population, " +
                "CASE WHEN co.Total_Population = 0 THEN 0 ELSE ROUND((co.City_Population / co.Total_Population) * 100, 2) END AS City_Pct, " +
                "co.Non_City_Population, " +
                "CASE WHEN co.Total_Population = 0 THEN 0 ELSE ROUND((co.Non_City_Population / co.Total_Population) * 100, 2) END AS Non_City_Pct " +
                "FROM (SELECT country.Name AS Country_Name, country.Population AS Total_Population, " +
                "COALESCE(SUM(city.Population), 0) AS City_Population, " +
                "country.Population - COALESCE(SUM(city.Population), 0) AS Non_City_Population " +
                "FROM country LEFT JOIN city ON country.Code = city.CountryCode GROUP BY country.Code, country.Name, country.Population) co ORDER BY co.Total_Population DESC";
        return fetchPopulationStats(con, sql);
    }

    /**
     * Issue #23 : Get population of world, continent, region, country, district, city
     *
     * @param con The active database connection.
     */
    public static ArrayList<World.PopulationStats> getWorldReport(Connection con) {
        String sql = "SELECT 'World' AS Level_Name, SUM(country.Population) AS Total_Pop, " +
                "COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode";
        return fetchPopulationStats(con, sql);
    }

    public static ArrayList<World.PopulationStats> getHierarchicalContinentReport(Connection con) {
        String sql = "SELECT country.Continent AS Level_Name, SUM(country.Population) AS Total_Pop, " +
                "COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode GROUP BY country.Continent ORDER BY Total_Pop DESC";
        return fetchPopulationStats(con, sql);
    }

    public static ArrayList<World.PopulationStats> getHierarchicalRegionReport(Connection con) {
        String sql = "SELECT country.Region AS Level_Name, SUM(country.Population) AS Total_Pop, " +
                "COALESCE(SUM(city_agg.City_Pop), 0) AS City_Pop, ROUND((COALESCE(SUM(city_agg.City_Pop), 0) / SUM(country.Population)) * 100, 2) AS City_Pct, " +
                "SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0) AS Non_City_Pop, " +
                "ROUND(((SUM(country.Population) - COALESCE(SUM(city_agg.City_Pop), 0)) / SUM(country.Population)) * 100, 2) AS Non_City_Pct " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode GROUP BY country.Region ORDER BY Total_Pop DESC";
        return fetchPopulationStats(con, sql);
    }

    public static ArrayList<World.PopulationStats> getHierarchicalCountryReport(Connection con) {
        String sql = "SELECT country.Name AS Level_Name, country.Population AS Total_Pop, " +
                "COALESCE(city_agg.City_Pop, 0) AS City_Pop, CASE WHEN country.Population = 0 THEN 0 ELSE ROUND((COALESCE(city_agg.City_Pop, 0) / country.Population) * 100, 2) END AS City_Pct, " +
                "country.Population - COALESCE(city_agg.City_Pop, 0) AS Non_City_Pop, " +
                "CASE WHEN country.Population = 0 THEN 0 ELSE ROUND(((country.Population - COALESCE(city_agg.City_Pop, 0)) / country.Population) * 100, 2) END AS Non_City_Pct " +
                "FROM country LEFT JOIN (SELECT CountryCode, SUM(Population) AS City_Pop FROM city GROUP BY CountryCode) city_agg " +
                "ON country.Code = city_agg.CountryCode ORDER BY Total_Pop DESC";
        return fetchPopulationStats(con, sql);
    }

    public static ArrayList<World.PopulationStats> getDistrictReport(Connection con) {
        String sql = "SELECT CONCAT(city.District, ' (', country.Name, ')') AS Level_Name, SUM(city.Population) AS Total_Pop, " +
                "SUM(city.Population) AS City_Pop, 100.00 AS City_Pct, 0 AS Non_City_Pop, 0.00 AS Non_City_Pct " +
                "FROM city JOIN country ON city.CountryCode = country.Code GROUP BY city.District, country.Name ORDER BY Total_Pop DESC";
        return fetchPopulationStats(con, sql);
    }

    public static ArrayList<World.PopulationStats> getCityReport(Connection con) {
        String sql = "SELECT CONCAT(city.Name, ' (', country.Name, ')') AS Level_Name, city.Population AS Total_Pop, " +
                "city.Population AS City_Pop, 100.00 AS City_Pct, 0 AS Non_City_Pop, 0.00 AS Non_City_Pct " +
                "FROM city JOIN country ON city.CountryCode = country.Code ORDER BY Total_Pop DESC";
        return fetchPopulationStats(con, sql);
    }

    /**
     * Issue #27 : Get population of people who speak Chinese, English, Hindi, Spanish, Arabic
     *
     * @param con The active database connection.
     */
    public static ArrayList<World.LanguageStats> getLanguageReport(Connection con) {
        String sql = "SELECT cl.Language, SUM(CAST(ROUND(c.Population * (cl.Percentage / 100)) AS UNSIGNED)) AS Total_Speakers, " +
                "ROUND((SUM(CAST(ROUND(c.Population * (cl.Percentage / 100)) AS UNSIGNED)) / (SELECT SUM(Population) FROM country)) * 100, 2) AS World_Percentage " +
                "FROM countrylanguage cl JOIN country c ON cl.CountryCode = c.Code " +
                "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') GROUP BY cl.Language ORDER BY Total_Speakers DESC";

        ArrayList<World.LanguageStats> statsList = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                World.LanguageStats stats = new World.LanguageStats();
                stats.setLanguage(rs.getString(1));
                stats.setTotalSpeakers(rs.getLong(2));
                stats.setWorldPercentage(rs.getDouble(3));
                statsList.add(stats);
            }
        } catch (SQLException e) {
            System.err.println("Error executing Language Report: " + e.getMessage());
        }
        return statsList;
    }

    private static ArrayList<World.PopulationStats> fetchPopulationStats(Connection con, String query) {
        ArrayList<World.PopulationStats> statsList = new ArrayList<>();
        try (PreparedStatement stmt = con.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                World.PopulationStats stats = new World.PopulationStats();
                stats.setAreaName(rs.getString(1));
                stats.setTotalPopulation(rs.getLong(2));
                stats.setCityPopulation(rs.getLong(3));
                stats.setCityPercentage(rs.getDouble(4));
                stats.setNonCityPopulation(rs.getLong(5));
                stats.setNonCityPercentage(rs.getDouble(6));
                statsList.add(stats);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching Population Stats: " + e.getMessage());
        }
        return statsList;
    }

    /**
     * Helper method for handling Continent, Region, Country reports
     *
     * @param title      The report title banner.
     * @param nameHeader The column header name for the grouped entity.
     * @param statsList  The Array list containing the data.
     */
    public static void printPopulationStats(String title, String nameHeader, ArrayList<World.PopulationStats> statsList) {
        System.out.println("\n" + "=".repeat(110));
        System.out.printf(" %-108s %n", title);
        System.out.println("=".repeat(110));
        System.out.format("| %-30s | %15s | %15s | %10s | %15s | %10s |%n", nameHeader, "Total Population", "City Population", "City Pop (%)", "Non-City Population", "Non-City (%)");
        System.out.println("-".repeat(110));

        if (statsList != null) {
            for (World.PopulationStats stats : statsList) {
                System.out.format("| %-30s | %,15d | %,15d | %9.2f%% | %,15d | %9.2f%% |%n",
                        truncate(stats.getAreaName(), 30), stats.getTotalPopulation(), stats.getCityPopulation(),
                        stats.getCityPercentage(), stats.getNonCityPopulation(), stats.getNonCityPercentage());
            }
        }
        System.out.println("=".repeat(110) + "\n");
    }

    /**
     * Helper method for handling broader hierarchical data reports (World, Continent, Region, Country, District, City)
     *
     * @param title      The report title banner.
     * @param nameHeader The column header name for the grouped entity.
     * @param statsList  The Array list containing the data.
     */
    public static void printUniversalStats(String title, String nameHeader, ArrayList<World.PopulationStats> statsList) {
        System.out.println("\n" + "=".repeat(110));
        System.out.printf(" %-108s %n", title);
        System.out.println("=".repeat(110));
        System.out.format("| %-35s | %15s | %15s | %10s | %15s | %10s |%n", nameHeader, "Total Population", "City Pop", "City (%)", "Non-City Pop", "Non-City (%)");
        System.out.println("-".repeat(110));

        if (statsList != null) {
            for (World.PopulationStats stats : statsList) {
                System.out.format("| %-35s | %,15d | %,15d | %9.2f%% | %,15d | %9.2f%% |%n",
                        truncate(stats.getAreaName(), 35), stats.getTotalPopulation(), stats.getCityPopulation(),
                        stats.getCityPercentage(), stats.getNonCityPopulation(), stats.getNonCityPercentage());
            }
        }
        System.out.println("=".repeat(110) + "\n");
    }

    public static void printLanguageStats(ArrayList<World.LanguageStats> statsList) {
        System.out.println("\n" + "=".repeat(75));
        System.out.printf(" %-73s %n", "POPULATION REPORT BY LANGUAGE (Chinese, English, Hindi, Spanish, Arabic)");
        System.out.println("=".repeat(75));
        System.out.format("| %-20s | %25s | %18s |%n", "Language", "Total Speakers", "Percentage (%)");
        System.out.println("-".repeat(75));

        if (statsList != null) {
            for (World.LanguageStats stats : statsList) {
                System.out.format("| %-20s | %,25d | %17.2f%% |%n",
                        truncate(stats.getLanguage(), 20), stats.getTotalSpeakers(), stats.getWorldPercentage());
            }
        }
        System.out.println("=".repeat(75) + "\n");
    }

    /**
     * Helper method to truncate long text strings to fit within table cell limits.
     *
     * @param text      The text string to truncate.
     * @param maxLength The maximum allowed character length.
     * @return The truncated string with ellipsis if it exceeds the limit.
     */
    private static String truncate(String text, int maxLength) {
        if (text == null) return "";
        return text.length() > maxLength ? text.substring(0, maxLength - 3) + "..." : text;
    }
}