# USE CASE: Generate Capital City Reports

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to know:
- all the capital cities in the world organised by largest population to smallest to analyze global capital city distribution.
- all the capital cities in a continent organised by largest population to smallest to compare capital city sizes across the continent.
- all the capital cities in a region organised by largest population to smallest to understand capital city distribution in the region.
- the top N populated capital cities in the world where N is provided by the user to analyze global capital city demographics.
- the top N populated capital cities in a continent where N is provided by the user to analyze continental capital city demographics.
- the top N populated capital cities in a region where N is provided by the user to analyze regional capital city demographics._

### Scope

Global, Continental, and Regional Capital City reporting system.

### Level

**Primary Task**

### Preconditions

The database contains accurate and up-to-date data on capital city populations along with their corresponding country, region, and continent. The user has authorized access to generate these reports.

### Success End Condition

The system successfully generates and displays reports on all capital cities or the top N populated capital cities of the world, continent, or region in accordance with the selected scope.

### Failed End Condition

No Report is produced or incomplete data is displayed.

### Primary Actor

_Data Analyst_

### Trigger

A request is initiated by the Data Analyst to generate capital city reports for a specific geographic scope.

### Main Success Scenario

1. The Data Analyst initiates a request for a capital city report.
2. The Data Analyst specifies the desired scope (world, continent, or region) and, if required, provides the value for N.
3. The system queries the database for the relevant capital city population data based on the provided scope and N value.
4. The system retrieves the requested data from the database.
5. The system sorts and organizes the retrieved data in descending order according to population.
6. The system generates and displays the requested report with appropriate columns and headings.

### Extension

2. **If scope, N value, or population data is missing or invalid:** System requests correction or notifies the user that no data is available.
   3.a. If the Data Analyst's selected scope is world, the system queries the relevant data columns: Name of Capital City, Country, Population.
   3.b. If the Data Analyst's selected scope is continent, the system queries the relevant data columns: Name of Capital City, Continent, Country, Population.
   3.c. If the Data Analyst's selected scope is region, the system queries the relevant data columns: Name of Capital City, Region, Country, Population.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0