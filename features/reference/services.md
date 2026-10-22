# Services reference

Current service surface for the Soccer League System (Feature 13).

## TeamService

| Method | Behavior |
| :--- | :--- |
| `createTeam(TeamRequest request)` | Creates and persists a team (`name`, `homeVenue`, `coachName`). Duplicate names throw `DuplicateException`. Blank names throw `IllegalArgumentException`. |
| `updateTeam(long teamId, TeamRequest request)` | Updates an existing team profile (name, home venue, coach name). Missing IDs throw `NotFoundException`. Duplicate names throw `DuplicateException`. |
| `getTeam(long teamId)` | Returns a team profile. Missing IDs throw `NotFoundException`. |
| `listTeams()` | Returns the active team list, ordered by name. |
| `registerPlayer(PlayerRequest request)` | Registers a player (`name`, `jerseyNumber`) on an existing team. Missing team IDs throw `NotFoundException`. |
| `listPlayers(long teamId)` | Returns players assigned to a team. |

## MatchService

| Method | Behavior |
| :--- | :--- |
| `scheduleMatch(MatchRequest request)` | Validates that home and away teams exist and are distinct, then persists a match with date/time. Status is `SCHEDULED`. Same-team matches throw `IllegalArgumentException`. Unknown team IDs throw `NotFoundException`. |
| `listMatches()` | Returns the schedule catalog ordered by match date. |

## Requests

- **TeamRequest:** `name`, `homeVenue`, `coachName`
- **PlayerRequest:** `teamId`, `name`, `jerseyNumber`
- **MatchRequest:** `homeTeamId`, `awayTeamId`, `matchDate`
