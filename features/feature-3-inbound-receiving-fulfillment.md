# Feature Specification: Inbound Receiving & Purchase Orders

## Header
* **Feature ID**: FEAT-03
* **Feature Name**: Inbound Receiving & Purchase Orders
* **Status**: Draft
* **Author**: Amith
* **Target Release**: v1.0

## User Stories
* **US3.1**: As a Warehouse Purchasing Agent, I want to create Purchase Orders (POs) for suppliers so that expected incoming stock can be tracked.
* **US3.2**: As a Receiving Clerk, I want to mark PO line items as received so that inventory levels update automatically.

## Functional Requirements
* **FR3.1**: The system shall support creating Purchase Orders with Supplier details and Line Items.
* **FR3.2**: A Purchase Order shall transition through statuses: `DRAFT`, `ISSUED`, `PARTIALLY_RECEIVED`, `COMPLETED`, `CANCELLED`.
* **FR3.3**: The system shall allow partial receiving of line item quantities.
* **FR3.4**: Receiving items shall automatically trigger a stock quantity increase in the selected storage location.

## Key Entities
* **Supplier**: Vendor details providing inventory.
* **PurchaseOrder**: Header entity representing an inbound shipment request.
* **POLineItem**: Individual inventory items and quantities associated with a Purchase Order.

## Initial Data Model
* **Supplier**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `company_name` (VARCHAR(100), Not Null)

* **PurchaseOrder**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `po_number` (VARCHAR(50), Unique, Not Null)
  * `supplier_id` (INTEGER, Foreign Key -> Supplier.id)
  * `status` (ENUM('DRAFT', 'ISSUED', 'PARTIALLY_RECEIVED', 'COMPLETED', 'CANCELLED'), Default: 'DRAFT')

* **POLineItem**:
  * `id` (INTEGER, Primary Key, Auto-Increment)
  * `po_id` (INTEGER, Foreign Key -> PurchaseOrder.id)
  * `item_id` (INTEGER, Foreign Key -> InventoryItem.id)
  * `expected_quantity` (INTEGER, Not Null)
  * `received_quantity` (INTEGER, Default: 0)

## Gherkin AC
```gherkin
Feature: Inbound Receiving

  Scenario: Receiving a line item completely updates PO status
    Given a Purchase Order "PO-9001" exists in "ISSUED" status with 1 line item for 20 units of "ELEC-1001"
    When the receiving clerk marks 20 units of "ELEC-1001" as received into "Aisle 1 - Bin 01"
    Then the line item received quantity should equal 20
    And the status of "PO-9001" should update to "COMPLETED"
```
