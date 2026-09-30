# USE CASE: Generate Population and Language Demographics Report

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to know the population of people, people living in cities, and people not living in cities in each continent to analyze global demographics._

_As a population data analyst, I want to know the population of people, people living in cities, and people not living in cities in each country to analyze national demographics._

_As a population data analyst, I want to know the population of people, people living in cities, and people not living in cities in each region to analyze local demographics._

_As a population data analyst, I want to know the population of world, continent, region, country, district, city to analyze population data on different levels._

_As a population data analyst, I want to view a report on the population of people who speak Chinese, English, Hindi, Spanish, and Arabic, so that I can analyze the global distribution and percentage of the world's most prominent languages._

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

1. The Data Analyst initiates a request for a population report/ language report.

2. The Data Analyst specifies the desired scope "world, continent, region, country, or district" or "Chinese, English, Hindi, Spanish, and Arabic".

3. The system queries the database for the relevant continent/country/region/district/city population data, including urban and non-urban population (or) queries the database for the relevant spoken language based on the scope.

4. The system sorts and organizes the data in descending order by population.

5. The system generates and displays the city report with appropriate columns.

### Extension

2. **If scope or population data is missing or invalid:** System requests correction or notifies the user that no data is available.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0