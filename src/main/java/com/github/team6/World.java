package com.github.team6;

/**
 * A centralized class containing all the data models (Getters & Setters)
 * used across the team's demographic reports.
 *
 * Using this allows the team to build ArrayLists of data for easy Unit Testing.
 */
public class World {

    // --------------------------------------------------------
    // 1. CITY MODEL (Used by CityReport & CityPopulationReport)
    // --------------------------------------------------------
    public static class City {
        private String name;
        private String country;
        private String district;
        private int population;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public String getDistrict() { return district; }
        public void setDistrict(String district) { this.district = district; }

        public int getPopulation() { return population; }
        public void setPopulation(int population) { this.population = population; }
    }

    // --------------------------------------------------------
    // 2. CAPITAL CITY MODEL (Used by CapitalCityReport)
    // --------------------------------------------------------
    public static class CapitalCity {
        private String name;
        private String country;
        private int population;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }

        public int getPopulation() { return population; }
        public void setPopulation(int population) { this.population = population; }
    }

    // --------------------------------------------------------
    // 3. COUNTRY MODEL (Used by CountryReport & CountryPopulationReport)
    // --------------------------------------------------------
    public static class Country {
        private String code;
        private String name;
        private String continent;
        private String region;
        private int population;
        private String capital; // Can store the name of the capital city

        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getContinent() { return continent; }
        public void setContinent(String continent) { this.continent = continent; }

        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }

        public int getPopulation() { return population; }
        public void setPopulation(int population) { this.population = population; }

        public String getCapital() { return capital; }
        public void setCapital(String capital) { this.capital = capital; }
    }

    // --------------------------------------------------------
    // 4. POPULATION STATS MODEL (Used by PopulationReport Issue #20-23)
    // --------------------------------------------------------
    public static class PopulationStats {
        private String areaName; // Can be Continent, Region, Country, etc.
        private long totalPopulation;
        private long cityPopulation;
        private double cityPercentage;
        private long nonCityPopulation;
        private double nonCityPercentage;

        public String getAreaName() { return areaName; }
        public void setAreaName(String areaName) { this.areaName = areaName; }

        public long getTotalPopulation() { return totalPopulation; }
        public void setTotalPopulation(long totalPopulation) { this.totalPopulation = totalPopulation; }

        public long getCityPopulation() { return cityPopulation; }
        public void setCityPopulation(long cityPopulation) { this.cityPopulation = cityPopulation; }

        public double getCityPercentage() { return cityPercentage; }
        public void setCityPercentage(double cityPercentage) { this.cityPercentage = cityPercentage; }

        public long getNonCityPopulation() { return nonCityPopulation; }
        public void setNonCityPopulation(long nonCityPopulation) { this.nonCityPopulation = nonCityPopulation; }

        public double getNonCityPercentage() { return nonCityPercentage; }
        public void setNonCityPercentage(double nonCityPercentage) { this.nonCityPercentage = nonCityPercentage; }
    }

    // --------------------------------------------------------
    // 5. LANGUAGE MODEL (Used by PopulationReport Issue #27)
    // --------------------------------------------------------
    public static class LanguageStats {
        private String language;
        private long totalSpeakers;
        private double worldPercentage;

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }

        public long getTotalSpeakers() { return totalSpeakers; }
        public void setTotalSpeakers(long totalSpeakers) { this.totalSpeakers = totalSpeakers; }

        public double getWorldPercentage() { return worldPercentage; }
        public void setWorldPercentage(double worldPercentage) { this.worldPercentage = worldPercentage; }
    }
}