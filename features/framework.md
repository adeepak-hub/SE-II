# Spec-Driven Development Framework

Spec first, code second, tests as proof. Feature files authorize *what* to build.

## Implement in layer order

1. Domain entities and request objects
2. Database schema and repositories
3. Services and product-rule exceptions
4. JUnit tests mapped to Gherkin scenarios
5. Swing UI panels

Work only from the feature spec. Do not implement behavior that is not specified.

## Test traceability

Every `#### Scenario` must appear in the **Test Coverage Map** and have at least one automated test before merge.

- Test class header cites the feature file.
- `@DisplayName` matches the Gherkin scenario title exactly.

Run tests from `app/`:

```bash
mvn test
```

## Living reference

When service methods, schema, or product rules change, update in the same change set:

- `features/reference/services.md`
- `features/reference/data-model.md`

## Merge checklist

- [x] Spec matches what shipped (stories, FR-00N, SC-00N, Screen/Data, Out of Scope)
- [x] Every Gherkin scenario has a real test; Test Coverage Map complete; `mvn test` passes
- [x] Living reference updated when schema or services changed
- [x] Catalog row added for new features
