# USE CASE: Generate Country Population Reports

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to:_

- see all the countries in the world organized by population, so that I can analyze global country demographics.
- see all the countries in a selected continent organized by population, so that I can analyze continental country demographics.
- see all the countries in a selected region organized by population, so that I can analyze regional country demographics.

### Scope

Global, Continental and Regional Country Population Reporting System.

### Level

**Primary Task**

### Preconditions

The database is available and contains country information, including country code, name, continent, region, population and capital. The Data Analyst has access to the system to request country population reports.

### Success End Condition

The system successfully generates and displays the requested report, containing all countries within the selected scope, sorted by population from largest to smallest, with the required information displayed.

### Failed End Condition

The requested report cannot be generated, the required data is unavailable, or the system cannot retrieve or display the report correctly.

### Primary Actor

_Data Analyst_

### Trigger

The Data Analyst requests a country population report for the world, a selected continent or a selected region.

### Main Success Scenario

1. The Data Analyst initiates a request to generate a country population report.
2. The Data Analyst selects the desired report scope: worldwide, continent, or region.
3. If the selected scope is a continent or region, the Data Analyst specifies the continent or region name.
4. The system queries the database to retrieve all countries matching the selected scope.
5. The system sorts the retrieved countries by population in descending order, from largest to smallest.
6. The system generates and displays the report with the following columns: Code, Name, Continent, Region, Population, Capital.

### Extensions

2. **If the selected report scope is invalid:** The system informs the Data Analyst and requests a valid selection.

3. **If the continent or region is missing or invalid:** The system requests a valid continent or region name.

4. **If no countries match the selected continent or region:** The system informs the Data Analyst that no data is available for the selected scope.

4.a. **If the database query fails or the required data cannot be retrieved:** The system informs the Data Analyst that the report cannot be generated.

6. **If the report cannot be displayed:** The system informs the Data Analyst that the report could not be displayed.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0