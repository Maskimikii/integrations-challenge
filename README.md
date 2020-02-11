Requirements
-
1. Java 11
2. Docker

Coding challenge
-

You have zip archive (./src/main/resources/data) which contains two files:
 * invoice.csv
 * invoice_lines.csv
 
This application has an endpoint "/bulk/invoices" which consumes list of JSON objects and service stub: 
com.yaypay.challenge.services.InvoiceDataService.

DB schema can be found there: /src/main/resources/db/migration/V0_0_0__database_init.sql
DB uses dynamic port so after each startup port will be changed,
the current one you can find in app logs, e.g.: "Database: jdbc:mysql://localhost:32802/challenge (MySQL 8.0)"

Your goals are:
 * Create simple application which reads CSV and sends data to "/bulk/invoices"
 * Implement method com.yaypay.challenge.services.InvoiceDataService.processData to persist the data.
 
 
Notes
-
In your application you can use any technology you want, however this application should not be changed 
(you can add required DTO/Entity objects but without changes in method signatures or adding extra dependencies)
  
 
  