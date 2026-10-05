# USE CASE: Generate Population and Language Demographics Report

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to know:
- the population of people, people living in cities, and people not living in cities in each continent to analyze global demographics.
- the population of people, people living in cities, and people not living in cities in each country to analyze national demographics.
- the population of people, people living in cities, and people not living in cities in each region to analyze local demographics.
- the population of world, continent, region, country, district, city to analyze population data on different levels.
- the population of people who speak Chinese, English, Hindi, Spanish, and Arabic, so that I can analyze the global distribution and percentage of the world's most prominent languages._

### Scope

Global, National, Local Population and Language reporting system.

### Level

**Primary Task**

### Preconditions

The database contains accurate and up-to-date data on global population and the number of speakers for each specified language. The user has authorized access to generate global/national/local demographic reports.

### Success End Condition

The system successfully generates and displays reports on total population of each continent/country/region, including urban and non-urban population, and language demographics in accordance with the selected scope.

### Failed End Condition

No Report is produced or incomplete data is displayed.

### Primary Actor

_Data Analyst_

### Trigger

A request is initiated by the Data Analyst to generate population and langugae demographic reports for global or localized analysis.

### Main Success Scenario

1. The Data Analyst initiates a request for a population (or) language demographic report.

2. The Data Analyst specifies the desired scope such as world, continent, region, country,district or selects Chinese, English, Hindi, Spanish, and Arabic.

3. The system queries the database for the relevant population data, including total, urban, and non-urban population, or retrieves the relevant language population data.

4. The system retrieves the requested data from the database.

5. The system sorts and organizes the retrieved data in descending order according to population.

6. The system generates and displays the requested report with appropriate columns and headings.

### Extension

2.**If scope or population data is missing or invalid:** System requests correction or notifies the user that no data is available.

3.a. If the Data Analyst's selected scope is continent, the system qureries the relevant data columns:Name of Continent, Total population of continent, total population of the continent living in cities (including %), total population of the continent not living in cities (including %).

3.b. If the Data Analyst's selected scope is country, the system qureries the relevant data columns:Name of Country, Total population of country, total population of the country living in cities (including %), total population of the country not living in cities (including %).

3.c. If the Data Analyst's selected scope is region, the system qureries the relevant data columns:Name of Region, Total population of region, total population of the region living in cities (including %), total population of the region not living in cities (including %).

3.d. If the Data Analyst wants to retrieve the population of the world/continent/region/country/district/city, the system qureries the relevant data columns: Total Population of Continent/Region/Country/District/City, Total population of the Continent/Region/Country living in cities (including %), total population of the Continent/Region/Country not living in cities (including %)

3.e. If the Data Analyst wants to retrieve the population on prominent languages of the world (Chinese, English, Hindi, Spanish, and Arabic), the system queries the relevant data columns: Total Population of Language Speakers, Percentage (%).

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0