# Introduction
## Functionality
The app displays the data usage from year 2008 to 2018.

### HomePage
Allows you to see the data consumption yearly. When there is a drop in usage in the quarter of the year, a image will be displayed to highlight the same.
Each result from api is kept in the database in records_data table where the list of year data quarterly stored. Each time an api is called, the same RecordsData record in the Database is updated with the new list of repository ids.

### Testing
The project uses both instrumentation tests that run on the device and local unit tests that run on your computer. To run both of them and generate a coverage report, you can run:

./gradlew fullCoverageReport (requires a connected device or an emulator)

### Local Unit Tests
ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository implementations.

### Repository Tests
Each Repository is tested using local unit tests with mock web service and mock database.

### Webservice Tests
The project uses MockWebServer project to test REST api interactions.

### Libraries
Android Support Library
Android Architecture Components
MVVM
Android Data Binding
Dagger 2 for dependency injection
Retrofit for REST api communication
RoomDB
espresso for UI tests
mockito for mocking in tests
