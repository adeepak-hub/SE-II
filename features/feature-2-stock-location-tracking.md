# Feature Specification: Stock & Location Tracking

## Header
* **Feature ID**: FEAT-02
* **Feature Name**: Stock & Location Tracking
* **Status**: Draft
* **Author**: Amith
* **Target Release**: v1.0

## User Stories
* **US2.1**: As a Warehouse Worker, I want to record stock adjustments so that physical counts match system records.
* **US2.2**: As a Warehouse Manager, I want to track stock quantities across specific aisles, racks, and shelf bins so that order picking is optimized.

## Functional Requirements
* **FR2.1**: The system shall maintain distinct warehouse locations identified by Aisle, Rack, and Shelf Bin.
* **FR2.2**: The system shall track current stock quantities mapped to specific locations.
* **FR2.3**: The system shall record stock adjustments with an audit reason (e.g., Received, Damaged, Relocated).
* **FR2.4**: The system shall prevent negative stock balances at any specific location.

## Key Entities
* **Location**: Represents a physical storage bin location in the warehouse.
* **StockLevel**: Represents the physical quantity of an item stored at a location.
* **StockTransaction**: Represents an audit record of stock quantity changes.

## Initial Data Model
* **Location**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `aisle` (VARCHAR(10), Not Null)
  * `rack` (VARCHAR(10), Not Null)
  * `bin` (VARCHAR(10), Not Null)

* **StockLevel**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `item_id` (INTEGER, Foreign Key -> InventoryItem.id)
  * `location_id` (INTEGER, Foreign Key -> Location.id)
  * `quantity` (INTEGER, Not Null, Check: quantity >= 0)

* **StockTransaction**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `item_id` (INTEGER, Foreign Key -> InventoryItem.id)
  * `location_id` (INTEGER, Foreign Key -> Location.id)
  * `quantity_change` (INTEGER, Not Null)
  * `transaction_type` (ENUM('RECEIVE', 'MOVE', 'ADJUSTMENT', 'PICK'), Not Null)
  * `reason` (VARCHAR(255), Nullable)
  * `created_at` (TIMESTAMP)

## Gherkin AC
```gherkin
Feature: Stock & Location Tracking

  Scenario: Receiving stock into a warehouse location
    Given an item "ELEC-1001" exists
    And location "Aisle 1 - Rack B - Bin 04" exists with 0 items
    When I receive 50 units of "ELEC-1001" into location "Aisle 1 - Rack B - Bin 04"
    Then the stock level for "ELEC-1001" at location "Aisle 1 - Rack B - Bin 04" should be 50
    And a transaction record of type "RECEIVE" for 50 units should be recorded
```
