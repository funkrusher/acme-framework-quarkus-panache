# Acme Framework Quarkus Panache

This is a minimal CRUD service exposing a couple of endpoints over REST.

Under the hood, the code is using:
- Quarkus Framework
  - RESTEasy to expose the REST endpoints
  - JUnit5 Unit-Testing
  - Db-Transactions safely coordinated by the Narayana Transaction Manager
  - Swagger-UI Support
- Mariadb-Testcontainer for Unit-Tests and Code-Generator
- Gradle Build
  - Multi-Module project for shared-libraries approach

## Requirements

To compile and run this demo you will need:

- Java JDK 21+
- Mariadb database
- Optional: Quarkus Plugin in Intellij-IDEA
- Docker with Docker-Compose

The project has been set up specifically with Intellij IDEA compatibility in mind.

## Setup

### Open the project

Open the project in Intellij IDEA

1. Make sure that the project does not contain the subfolders: `.idea` and `.gradle`, and also delete all `build` folders within the projects subdirectories.
2. Now open the project via `File`->`Open`.
3. The project should now be build automatically.

### Configure application.properties

Copy the file `acme-panache-backend/src/main/resources/application-template.properties` to `application.properties`.

Edit the file `acme-panache-backend/src/main/resources/application.properties` in your editor of choice and set the following settings for a connection with your Mariadb database.
```code
quarkus.datasource.jdbc.url=jdbc:mariadb://localhost:3306/testshop
quarkus.datasource.username=xxx
quarkus.datasource.password=xxx
```

## Start

Start the Demo from the Console with following command:
```code
./gradlew --console=plain quarkusDev
```
You can then navigate your webbrowser directly to the swagger-ui:
- http://localhost:8080/q/swagger-ui/

-----------------------------------


docker-compose -f docker-compose-database1.yml up --build -d

./gradlew 

https://chatgpt.com/share/6827a01a-2a4c-8001-95d2-b839e911c2f6

