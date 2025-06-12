# TutorBillingApp

TutorBillingApp is an Android application for managing tutoring sessions and invoices. It uses Jetpack Compose and Room for local data storage.

## Prerequisites

- **JDK 17 or newer** installed and available on your `PATH`.
- **Android SDK** with API level 35 and build tools. The repository provides a helper script that installs the required SDK packages.

## Mandatory Android SDK setup

The project cannot be built until the Android SDK is installed. Use the provided
setup script to download the required packages and configure the environment:

```bash
bash setup-android-sdk.sh
source ~/.bashrc   # or the profile file printed by the script
```

Running the script once will download the command line tools and create a
`local.properties` file pointing Gradle to the SDK. Make sure to source your
profile before invoking any Gradle tasks so that the `ANDROID_HOME` variables are
available.

## Building the project

Use the Gradle wrapper to build and test the app:

```bash
./gradlew assemble   # compiles all modules
./gradlew test       # runs unit tests
```

## Modules overview

- **app** – main Compose application module containing UI, Hilt dependency injection and Room database code.

## Database migrations

Room is configured with `autoMigrations` for database version upgrades. The
generated schema files are stored under `app/schemas` via the
`room.schemaLocation` Gradle argument. Since auto-migration handles the schema
changes, the project does not keep SQL scripts under
`app/src/main/assets/migrations`.

