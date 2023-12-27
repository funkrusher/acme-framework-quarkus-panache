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

### Create the database

Connect with your mariadb-database and create the database:
```
CREATE DATABASE testhop;
```

### Setup AWS Cognito Authentication/Authorization with OIDC

The example wants to show how to do Authentication and Authorization with an OIDC Provider.
To make it easy for local development, we will use an offline emulator for Amazon Cognito here.
The official offline emulator (`localstack`) can not be used, because most features would require a professional license.
Therefor we will use the `cognito-local` offline emulator:
- https://github.com/jagregory/cognito-local

We will first start `cognito-local` as a docker-container running on port 9229:
```
docker-compose -f cognito-local-docker-compose.yml up --build -d
```

Start the cognitoLocalSetup task from the Console with following command:
```code
gradlew cognitoLocalSetup
```
please note down the following three outputs of this task:
- cognitolocal.userpoolid
- cognitolocal.userpoolclientid
- cognitolocal.userpoolclientsecret

copy those three outputs directly into your `acme-panache-backend/src/main/resources/application.properties` file.
For example:
```
# cognito-local
cognitolocal.userpoolid=local_7GsYn8Qh
cognitolocal.userpoolclientid=67jqekw6w9193e8khcu9d5slh
cognitolocal.userpoolclientsecret=6sjqzo1wyemkrjecj4qlqembt
```

Quarkus will take care of the JWT-Verify, for the JWT that has been created by a successful AWS-Cognito Authentication.
We need to tell it where to get the OIDC configuration. So make sure that your `application.properties` file also contains the following configurations from the template
(please insert the correct value for <cognitolocal.userpoolid>):
```
# quarkus oidc
quarkus.oidc.auth-server-url=http://localhost:9229/<cognitolocal.userpoolid>
quarkus.oidc.discovery-enabled=false
quarkus.oidc.jwks-path=http://localhost:9229/<cognitolocal.userpoolid>/.well-known/jwks.json
quarkus.oidc.roles.role-claim-path=custom:acme_roles
```

## Start

Start the Demo from the Console with following command:
```code
./gradlew --console=plain quarkusDev
```
You can then navigate your webbrowser directly to the swagger-ui:
- http://localhost:8080/q/swagger-ui/

Within the Swagger-UI:

Call the following REST-Endpoint and give it an email+password to create a new user in the pool. Also make sure to give "ADMIN" as the roleId, and the clientId=1 (which we assume as a master-tenant-id that has privileged access)
- `/api/v1/cognitoLocal/signup`

Call the following REST-Endpoint and give it the same email+password, to obtain an access_token as response (and in the cookies)
- `/api/v1/cognitoLocal/signin`

Click the `Authorize`-Button in swagger-ui and enter the field `access_token (http, Bearer)` with the content of the response of the `/api/v1/cognitoLocal/signin` call. Note that the return of the signin-call does contain the id-token. This is necessary because aws-cognito does not provide all the information we need directly in the access-token. We can/need to use the id-token instead.

You have now setup swagger-ui to always provide this `access_token` as an Authorization.

Now you can finally call the following REST-Endpoint:
- `/api/v1/cognitoLocal/protected-by-quarkus`

Quarkus will automatically check the Authorization HTTP-Header in the request, which contains the access_token.
Quarkus will then validate this access_token with help of the `quarkus.oidc.jwks-path` you provided in the `application.properties` file.
This REST-Endpoint will only return Success, if the JWT-Verify is successful. 

The REST-Endpoint will also make sure that the user must have the "ADMIN" Role (RBAC), and the clientId=1 (Master-Tenant),
for access to be allowed.
