# USE CASE: Generate City-Population Reports

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to know:
- the top N populated cities in the world where N is provided by the user to analyze global demographics.
- the top N populated cities in a continent where N is provided by the user to analyze continental demographics.
- the top N populated cities in a region where N is provided by the user to analyze regional demographics.
- the top N populated cities in a country where N is provided by the user to analyze national demographics.
- the top N populated cities in a district where N is provided by the user to analyze local demographics._

### Scope

Global, Continental, Regional, National, Local City-Population reporting system.

### Level

**Primary Task**

### Preconditions

The database contains accurate and up-to-date data on city populations along with their corresponding district, country, region, and continent. The user has authorized access to generate these reports.

### Success End Condition

The system successfully generates and displays reports on the top N populated cities of the world, continent, region, country, or district in accordance with the selected scope.

### Failed End Condition

No Report is produced or incomplete data is displayed.

### Primary Actor

_Data Analyst_

### Trigger

A request is initiated by the Data Analyst to generate top N city-population reports for a specific geographic scope.

### Main Success Scenario

1. The Data Analyst initiates a request for a city-population report.
2. The Data Analyst specifies the desired scope (world, continent, region, country, or district) and provides the value for N.
3. The system queries the database for the relevant city population data based on the provided scope and N value.
4. The system retrieves the requested data from the database.
5. The system sorts and organizes the retrieved data in descending order according to population.
6. The system generates and displays the requested report with appropriate columns and headings.

### Extension

2. **If scope, N value, or population data is missing or invalid:** System requests correction or notifies the user that no data is available.
   3.a. If the Data Analyst's selected scope is world, the system queries the relevant data columns: Name of City, Country, District, Population.
   3.b. If the Data Analyst's selected scope is continent, the system queries the relevant data columns: Name of City, Continent, Country, District, Population.
   3.c. If the Data Analyst's selected scope is region, the system queries the relevant data columns: Name of City, Region, Country, District, Population.
   3.d. If the Data Analyst's selected scope is country, the system queries the relevant data columns: Name of City, Country, District, Population.
   3.e. If the Data Analyst's selected scope is district, the system queries the relevant data columns: Name of City, District, Population.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0