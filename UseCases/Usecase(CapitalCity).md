# Capital City Use Cases

---

## Use Case: All Capital Cities in the World

**Goal in Context:**
As a population data analyst, I want to view a report of all the capital cities in the world organized by largest population to smallest, so that I can analyze global capital city populations.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.

**Success Condition:**
A report is produced listing all capital cities in the world sorted by population (largest to smallest).

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "All Capital Cities in the World" report.

**Main Success Scenario:**
1. User selects "All Capital Cities in the World" report.
2. System queries the database for all capital cities.
3. System retrieves name, country, and population for each capital city.
4. System sorts by population (largest to smallest).
5. System displays the report.

**Extensions:**
- 2a. Database connection fails → System shows "Database error".

**Report Format:** Name, Country, Population.

---

## Use Case: All Capital Cities in a Continent

**Goal in Context:**
As a population data analyst, I want to view a report of all the capital cities in a specific continent organized by largest population to smallest, so that I can compare capital city sizes across the continent.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.
- A valid continent name is provided.

**Success Condition:**
A report is produced listing all capital cities in the continent sorted by population.

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "Capital Cities by Continent" and enters a continent name.

**Main Success Scenario:**
1. User selects "Capital Cities by Continent".
2. User enters continent name.
3. System queries the database for capital cities in that continent.
4. System retrieves name, country, and population.
5. System sorts by population (largest to smallest).
6. System displays the report.

**Extensions:**
- 2a. Continent name invalid → System shows "No data found".
- 3a. Database fails → System shows "Database error".

**Report Format:** Name, Country, Population.

---

## Use Case: All Capital Cities in a Region

**Goal in Context:**
As a population data analyst, I want to view a report of all the capital cities in a specific region organized by largest population to smallest, so that I can understand capital city distribution in that region.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.
- A valid region name is provided.

**Success Condition:**
A report is produced listing all capital cities in the region sorted by population.

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "Capital Cities by Region" and enters a region name.

**Main Success Scenario:**
1. User selects "Capital Cities by Region".
2. User enters region name.
3. System queries the database for capital cities in that region.
4. System retrieves name, country, and population.
5. System sorts by population (largest to smallest).
6. System displays the report.

**Extensions:**
- 2a. Region name invalid → System shows "No data found".
- 3a. Database fails → System shows "Database error".

**Report Format:** Name, Country, Population.

---

## Use Case: Top N Capital Cities in the World

**Goal in Context:**
As a population data analyst, I want to input N to see the top N populated capital cities in the world, so that I can analyze global capital city demographics.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.
- A positive integer N is provided.

**Success Condition:**
A report is produced showing the top N capital cities in the world by population.

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "Top N Capital Cities in the World" and enters N.

**Main Success Scenario:**
1. User selects "Top N Capital Cities in the World".
2. User enters N.
3. System validates N.
4. System queries the database for all capital cities in the world.
5. System sorts by population (largest to smallest).
6. System returns the top N.
7. System displays the report.

**Extensions:**
- 2a. N is not a positive integer → System shows "Invalid input".
- 3a. Database fails → System shows "Database error".

**Report Format:** Name, Country, Population.

---

## Use Case: Top N Capital Cities in a Continent

**Goal in Context:**
As a population data analyst, I want to input N to see the top N populated capital cities in a specific continent, so that I can analyze continental capital city demographics.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.
- A valid continent name and positive integer N are provided.

**Success Condition:**
A report is produced showing the top N capital cities in the continent by population.

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "Top N Capital Cities in a Continent" and enters continent and N.

**Main Success Scenario:**
1. User selects "Top N Capital Cities in a Continent".
2. User enters continent name and N.
3. System validates input.
4. System queries the database for capital cities in the continent.
5. System sorts by population (largest to smallest).
6. System returns the top N.
7. System displays the report.

**Extensions:**
- 2a. Input invalid → System shows "Invalid input".
- 3a. Continent not found → System shows "No data found".

**Report Format:** Name, Country, Population.

---

## Use Case: Top N Capital Cities in a Region

**Goal in Context:**
As a population data analyst, I want to input N to see the top N populated capital cities in a region, so that I can analyze regional capital city demographics.

**Scope:** Population Reporting System

**Level:** Primary task

**Preconditions:**
- User is authenticated.
- Population database is available.
- A valid region name and positive integer N are provided.

**Success Condition:**
A report is produced showing the top N capital cities in the region by population.

**Failed Condition:**
No report produced; an error message is shown.

**Primary Actor:** Population Data Analyst

**Trigger:** User selects "Top N Capital Cities in a Region" and enters region and N.

**Main Success Scenario:**
1. User selects "Top N Capital Cities in a Region".
2. User enters region name and N.
3. System validates input.
4. System queries the database for capital cities in the region.
5. System sorts by population (largest to smallest).
6. System returns the top N.
7. System displays the report.

**Extensions:**
- 2a. Input invalid → System shows "Invalid input".
- 3a. Region not found → System shows "No data found".

**Report Format:** Name, Country, Population.