# Data model reference

Current schema for the Soccer League System (Feature 13). Persisted in H2.

## teams

| Column | Notes |
| :--- | :--- |
| id | Primary key, generated |
| name | Required, unique |
| home_venue | Optional |
| coach_name | Optional |

## players

| Column | Notes |
| :--- | :--- |
| id | Primary key, generated |
| team_id | Foreign key → `teams.id` |
| name | Required |
| jersey_number | Optional integer |

## matches

| Column | Notes |
| :--- | :--- |
| id | Primary key, generated |
| home_team_id | Foreign key → `teams.id` |
| away_team_id | Foreign key → `teams.id` |
| match_date | Required timestamp |

Match **status** is a domain value (`SCHEDULED` on create). It is not a database column.
