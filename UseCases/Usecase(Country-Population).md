# USE CASE: Generate Country-Population Reports

## CHARACTERISTIC INFORMATION

### Goals in Context

_As a population data analyst, I want to know:

- All the countries in the world organized by largest population to smallest.
- All the countries in a continent organized by largest population to smallest.
- All the countries in a region organized by largest population to smallest.

### Scope

Global, Continental, and Regional Country-Population reporting system.

### Level

**Primary Task**

### Preconditions

The database contains accurate and up-to-date data on country populations along with their corresponding continent and region. The user has authorized access to generate these reports.

### Success End Condition

The system successfully generates and displays reports on countries organized by largest population to smallest based on the selected geographic scope (world, continent, or region).

### Failed End Condition

No report is produced or incomplete data is displayed.

### Primary Actor

_Organization Member / Data Analyst_

### Trigger

A request is initiated by the user to generate country-population reports for a specific geographic scope.

### Main Success Scenario

1. The user initiates a request for a country-population report.
2. The user specifies the desired geographic scope (world, continent, or region).
3. The system queries the database for the relevant country population data based on the selected scope.
4. The system retrieves the requested data from the database.
5. The system sorts and organizes the retrieved data in descending order according to population.
6. The system generates and displays the requested report with appropriate columns and headings.

### Extension

2. **If scope or population data is missing or invalid:** System requests correction or notifies the user that no data is available.
   3.a. If the selected scope is world, the system queries the relevant data columns: Country Code, Country Name, Continent, Region, Population, Capital.
   3.b. If the selected scope is continent, the system prompts for the target continent name and queries the relevant data columns: Country Code, Country Name, Continent, Region, Population, Capital.
   3.c. If the selected scope is region, the system prompts for the target region name and queries the relevant data columns: Country Code, Country Name, Continent, Region, Population, Capital.

### SUB-VARIATIONS

None

### SCHEDULE

**DUE DATE:** Release 1.0