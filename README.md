## flight-info

A Spring Boot REST API that returns a sorted list of flights for a given day of the week.

### Start-up

A csv file containing the flight records is required to run the service.
The file path must be set as an environment variable named 'filePath'. 
This can be defined as a command line variable when starting the application 
or by creating a properties file within the application.
Note - the flight info csv is outside the application as we can override the file without building new package.
e.g -DfilePath="C:\testData\flight_info.csv"

### Usage

Once the application is running, data can be accessed by sending GET requests to the /flights/{date} API
where {date} is a date in yyyy-MM-dd format.

e.g. GET /flights/2022-05-26