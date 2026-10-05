USE CASE: Generate City-Population Reports

CHARACTERISTIC INFORMATION

Goals in Context

_As a population data analyst, I want to know:

the top N populated cities in the world where N is provided by the user, to analyze global demographics.

the top N populated cities in a continent where N is provided by the user, to analyze continental demographics.

the top N populated cities in a region where N is provided by the user, to analyze regional demographics.

the top N populated cities in a country where N is provided by the user, to analyze national demographics.

the top N populated cities in a district where N is provided by the user, to analyze local demographics._

Scope

World, Continental, Regional, National, and District City-Population reporting system.

Level

Primary Task

Preconditions

The database contains accurate and up-to-date population data for all cities along with their corresponding district, country, region, and continent classifications. The Data Analyst has access to the system and provides a valid integer $N$.

Success End Condition

The system successfully generates and displays a report listing the top $N$ populated cities sorted in descending order of population, matching the specified geographic boundary.

Failed End Condition

No report is produced or an error message is displayed due to invalid input parameters or database connection failure.

Primary Actor

Data Analyst

Trigger

A request is initiated by the Data Analyst to generate a Top N city-population report for global or localized demographic analysis.

Main Success Scenario

The Data Analyst initiates a request for a Top N city-population report.

The Data Analyst specifies the desired geographic scope (world, continent, region, country, or district) and provides the positive integer value $N$.

The system queries the database for the relevant city population records within the selected scope, limiting the output to the top $N$ records.

The system retrieves the requested dataset from the database.

The system sorts and organizes the retrieved data in descending order according to population.

The system generates and displays the requested report with the appropriate columns and headings.

Extension

2.a. If geographic scope is missing or invalid: System requests correction or notifies the Data Analyst that no matching geographic records were found.

2.b. If parameter $N$ is missing, non-numeric, zero, or negative ($N \le 0$): System notifies the Data Analyst and requests a valid positive whole number.

3.a. If the Data Analyst's selected scope is world, the system queries the top $N$ populated cities globally with columns: City Name, Country, District, and Population.

3.b. If the Data Analyst's selected scope is continent, the system queries the top $N$ populated cities in the specified continent with columns: City Name, Country, District, and Population.

3.c. If the Data Analyst's selected scope is region, the system queries the top $N$ populated cities in the specified region with columns: City Name, Country, District, and Population.

3.d. If the Data Analyst's selected scope is country, the system queries the top $N$ populated cities in the specified country with columns: City Name, Country, District, and Population.

3.e. If the Data Analyst's selected scope is district, the system queries the top $N$ populated cities in the specified district with columns: City Name, Country, District, and Population.

4.a. Database connection failure: The system logs the failure and informs the Data Analyst that the database service is currently unavailable.

SUB-VARIATIONS

None

SCHEDULE

DUE DATE: Release 1.0