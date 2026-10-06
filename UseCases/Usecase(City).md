# USE CASE: Generate All Cities Reports

## CHARACTERISTIC INFORMATION

### Goals in Context

As a population data analyst, I want to know:
- all the cities in the world organised by largest population to smallest to analyze global demographics.
- all the cities in a continent organised by largest population to smallest to analyze continental demographics.
- all the cities in a region organised by largest population to smallest to analyze regional demographics.
- all the cities in a country organised by largest population to smallest to analyze national demographics.
- all the cities in a district organised by largest population to smallest to analyze local demographics.

### Scope

Global, Continental, Regional, National, Local City reporting system.

### Level

**Primary Task**

### Preconditions

The database contains accurate and up-to-date data on city populations along with their corresponding district, country, region, and continent. The user has authorized access to generate global/national/local demographic reports.

### Success End Condition

The system successfully generates and displays reports on all cities within a continent, country, region, district, or the world, ordered by population, in accordance with the selected scope.

### Failed End Condition

No Report is produced or incomplete data is displayed.

### Primary Actor

_City Planner_

### Trigger

A request is initiated by the City Planner to generate all cities reports for global or localized analysis.

### Main Success Scenario

1. The City Planner initiates a request for a city population report.

2. The City Planner specifies the desired scope such as world, continent, region, country, or district.

3. The system queries the database for all city population data based on the provided scope.

4. The system retrieves the requested data from the database.

5. The system sorts and organizes the retrieved data in descending order according to population.

6. The system generates and displays the requested report with appropriate columns and headings.

### Extension

2.**If scope or population data is missing or invalid:** System requests correction or notifies the user that no data is available.

3.a. If the City Planner's selected scope is world, the system queries the relevant data columns: Name, Country, District, Population.

3.b. If the City Planner's selected scope is continent, the system queries the relevant data columns: Name, Country, District, Population.

3.c. If the City Planner's selected scope is region, the system queries the relevant data columns: Name, Country, District, Population.

3.d. If the City Planner's selected scope is country, the system queries the relevant data columns: Name, Country, District, Population.

3.e. If the City Planner's selected scope is district, the system queries the relevant data columns: Name, Country, District, Population.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0