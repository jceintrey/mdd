# MDD App back

The Backend is based on Java21 using the SpringBoot 3.4.x Framework. It is a standard structured Rest Api application.
It uses

* Spring Security Framework manage authentication and authorizations to api endpoints
* OAuth2 Resssource Server Library to handle jwt operations
* Lombok to manage boilerplate code
* modelMapper library to handle conversions between DTOs and Entities
* jpa/hibernate as ORM for data persistence

# Setup

## Prerequisites

* Maven
* Java 21
* Mysql Server

## Install the application

### Clone the repository

```bash
git clone git@github.com:jceintrey/mdd.git
```

### Install dependencies

Go to the project folder

```bash
cd mdd/back
```

Install the project

```bash
mvn clean install
```

If there is a database connection error, the build will fail. You can skip the database access tests and come back to them later.

## Install and prepare the database

Once Mysql Server is installed, you have to configure a RW user for the application and adapt application.properties in back/src/main/resources/ to fit the databasename, username and password.

```bash
- MDD_DBURL
- MDD_DBUSER
- MDD_DBKEY
```

```bash
create database mdd;
CREATE USER 'mddappuser'@'%' IDENTIFIED BY 'heresetastrongpassword';
GRANT ALL PRIVILEGES ON mddappuser.* TO 'mddappuser'@'%';
flush privileges;
```

You could also use a simple compose file to run a mysql server. You will find an example in docker-compose.yml, with .env-mysql-sample file as an example.

## Running the application

To run the backend application,

```bash
mvn spring-boot:run
```
