# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]
### Added
- Student archive and restore flow with Archived Students screen.

## [0.12] - 2025-06-14
### Changed
- Invoice route now supports optional student selection

## [0.11] - 2025-06-14
### Added
- Display outstanding debts per student in the Revenue screen.
- `StudentDebt` data class annotated with `@Stable`.
- Ability to mark lessons paid and navigate to Invoice with pre-selected student.
- Reminder share option for unpaid debts.

## 0.10 - 2025-06-14
### Added
- Student management screens with class grouping and detail views.
- Lesson tracking with billing type support.
- Invoice creation with PDF generation and past invoices list.
- Revenue dashboard and settings screen.
- Setup script for Android SDK.

### Changed
- Navigation graph refactored for type-safe routes.
- Removed manual Room migration scripts in favour of auto-migrations.