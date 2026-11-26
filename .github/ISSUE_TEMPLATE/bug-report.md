---
name: Bug Report
about: Report a defect, exception, or incorrect query/output behaviour in the application
title: "[Bug]"
labels: ''
assignees: ''
type: Bug

---

## Summary
A clear and concise description of what is broken.

## Affected Files & Methods
- **Class / File:** `src/main/java/com/github/team6/<ClassName>.java`
- **Method(s) / Line(s):** `<methodName>(...)` (around line `<lineNumber>`)

## Environment
- **Java / Maven:** JDK 25, Maven
- **Database / Runtime:** Local MySQL (`localhost:33060/world`) or Docker Compose (`db:3306/world`)
- **Test / Build Context:** Unit test, integration test (`*IntegrationTest`), or `Main` execution

## Steps to Reproduce
1. Start or configure the environment (e.g., running `world` database or passing a `null` connection).
2. Call `<ClassName>.<methodName>(...)` with `<input values>`.
3. Observe the output, exception, or test result.

## Expected Behavior
What should happen (e.g., return an empty `ArrayList`, populate `Country` and `District`, or close JDBC resources cleanly).

## Actual Behavior
What actually happens (include stack traces, wrong output values, or leaked resources).
