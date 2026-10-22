# Feature: Soccer League System
**Feature ID:** 13
**Branch pattern:** feature/13-soccer-league
**Status:** Ready
**Created:** 2026-10-21
**Input:** Write a set of feature specs for the Soccer League System
**Depends on:** [feature-1-user-authorization.md](feature-1-user-authorization.md)

# User Stories
### US-13.1: Register Teams and Players
**As a** league administrator,
**I want to** register teams and players into the system,
**So that** the league roster is accurately maintained.
**Priority:** P1
**Independent test:** Verify that a new team can be created and persisted with associated players.
**Acceptance scenarios:** see ### US-13.1 under Acceptance Criteria

### US-13.2: Schedule Matches
**As a** league administrator,
**I want to** schedule matches between registered teams,
**So that** the tournament timeline is established without venue conflicts.
**Priority:** P1
**Independent test:** Verify that a match can be successfully scheduled between two distinct teams.
**Acceptance scenarios:** see ### US-13.2 under Acceptance Criteria

# Requirements
### Functional Requirements
- **FR-001:** The system MUST allow administrators to create, update, and view team profiles including team name and home venue.
- **FR-002:** The system MUST allow administrators to register players and assign them to a specific team.
- **FR-003:** The system MUST allow administrators to schedule matches specifying home team, away team, and match date/time.
- **FR-004:** The system MUST prevent scheduling a match where a team plays against itself.

# Assumptions
- Teams must be registered before they can be scheduled for matches.
- A single venue is sufficient for initial scheduling constraints.

# Edge Cases
- Registering a team with an existing name throws a DuplicateException.
- Scheduling a match with non-existent team IDs throws a NotFoundException.

# Success Criteria
- **SC-001:** Every Gherkin scenario has at least one automated test before merge.
- **SC-002:** Administrators can successfully manage teams and match schedules.

# Key Entities
- **Team:** TeamID, Name, HomeVenue, CoachName
- **Player:** PlayerID, TeamID, Name, JerseyNumber
- **Match:** MatchID, HomeTeamID, AwayTeamID, MatchDate, Status

# Service Requirements
| Service | Method | Notes |
| :--- | :--- | :--- |
| TeamService | createTeam(TeamRequest request) | Creates and saves a new team record. |
| MatchService | scheduleMatch(MatchRequest request) | Validates teams and schedules a new match. |

# Screen Requirements
- **Team Management Panel:** Form fields for Team Name, HomeVenue, and Coach Name with a primary "Save Team" button.
- **Match Schedule Panel:** Grid view showing scheduled matches with input controls to assign teams and dates.

# Data Model Requirements
| Table | Columns / Notes |
| :--- | :--- |
| teams | id (PK), name (unique, required), home_venue, coach_name |
| players | id (PK), team_id (FK), name, jersey_number |
| matches | id (PK), home_team_id (FK), away_team_id (FK), match_date |

# Acceptance Criteria (Gherkin)
### US-13.1 Register Teams and Players
#### Scenario: Administrator registers a new team successfully
- **Given** the administrator is on the team management screen
- **When** they enter valid team details and click save
- **Then** the team is stored in the system database
- **And** appears in the active team list

### US-13.2 Schedule Matches
#### Scenario: Administrator schedules a valid match
- **Given** two distinct teams are registered in the system
- **When** the administrator creates a match schedule with a valid date
- **Then** the match is successfully added to the schedule catalog

# Test Coverage Map
| Story | Scenario | Test file | Display name |
| :--- | :--- | :--- | :--- |
| US-13.1 | Administrator registers a new team successfully | app/src/test/java/edu/oc/pos/service/TeamServiceTest.java | @DisplayName("Administrator registers a new team successfully") |
| US-13.2 | Administrator schedules a valid match | app/src/test/java/edu/oc/pos/service/MatchServiceTest.java | @DisplayName("Administrator schedules a valid match") |

## Agent implementation request
Copy when asking Cursor to implement this feature (@this file):
```text
Implement Feature 13 from @features/feature-13-soccer-league.md on branch feature/13-soccer-league.
Follow layer order in @features/framework.md (domain -> repository -> service -> JUnit tests -> Swing UI).
Map every Gherkin scenario in the Test Coverage Map; run mvn test (from app/) before finishing.
If service methods, schema, or product rules changed per this spec, update @features/reference/services.md and @features/reference/data-model.md.
Complete Definition of Done and the merge checklist in @features/framework.md.
Do not implement behavior not in this spec.
```

## Definition of Done

*   [x] Domain, repository, service, tests, and Swing UI implemented per this spec (**FR-00N** satisfied)
*   [x] **Success Criteria (SC-00N)** met
*   [x] All mapped tests pass (`mvn test` from `app/`)
*   [x] Test Coverage Map complete
*   [x] `features/reference/data-model.md` updated
*   [x] `features/reference/services.md` updated

## Out of Scope

- User login and role enforcement (Feature 1)
- Venue conflict detection beyond storing a team's home venue
- Match scoring, standings, and status transitions after scheduling
