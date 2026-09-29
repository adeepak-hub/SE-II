# Feature Specification: Inventory Item Management

## Header
* **Feature ID**: FEAT-01
* **Feature Name**: Inventory Item Management
* **Status**: Draft
* **Author**: Amith
* **Target Release**: v1.0

## User Stories
* **US1.1**: As a Warehouse Manager, I want to create, update, and view inventory items so that the warehouse catalog accurately reflects available products.
* **US1.2**: As a Warehouse Clerk, I want to search and filter items by SKU, name, or category so that I can quickly locate product information.

## Functional Requirements
* **FR1.1**: The system shall allow authorized users to add new inventory items with mandatory fields: SKU, Name, Description, Unit Price, and Category.
* **FR1.2**: The system shall enforce uniqueness on the SKU field across all active inventory items.
* **FR1.3**: The system shall allow users to edit item details (Name, Description, Unit Price, Category).
* **FR1.4**: The system shall support archiving items to preserve historical transactional records.
* **FR1.5**: The system shall provide a paginated list view with search filters for SKU, Name, and Category.

## Key Entities
* **InventoryItem**: Represents a distinct product tracked within the warehouse.
* **Category**: Represents logical product groupings (e.g., Electronics, Perishables, Hardware).

## Initial Data Model
* **InventoryItem**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `sku` (VARCHAR(50), Unique, Not Null)
  * `name` (VARCHAR(100), Not Null)
  * `description` (TEXT, Nullable)
  * `unit_price` (DECIMAL(10,2), Not Null)
  * `category_id` (INTEGER, Foreign Key -> Category.id)
  * `is_archived` (BOOLEAN, Default: false)
  * `created_at` (TIMESTAMP)
  * `updated_at` (TIMESTAMP)

* **Category**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `name` (VARCHAR(50), Unique, Not Null)
  * `description` (VARCHAR(255), Nullable)

## Gherkin AC
```gherkin
Feature: Inventory Item Management

  Scenario: Successfully creating a new inventory item
    Given I am logged in as a Warehouse Manager
    When I submit a new item with SKU "ELEC-1001", Name "Barcode Scanner", Unit Price 49.99, and Category "Electronics"
    Then the item should be successfully created in the system
    And I should see "ELEC-1001" listed in the inventory catalog

  Scenario: Attempting to create an item with a duplicate SKU
    Given an item exists with SKU "ELEC-1001"
    When I attempt to create a new item with SKU "ELEC-1001"
    Then the system should reject the request with an error "SKU already exists"
```
