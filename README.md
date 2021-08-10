# calculator.api
Calculator service

REST API for the Calculator.WebApp project.

Current version: 1

## Live App
https://jmsalcidocalculator.herokuapp.com

## REST Documentation
[Over here...](DOCUMENTATION.md)

## Major Dependencies

- Java 11+
- PostgreSQL 10+
- Gradle 3+

## How to build

This is a gradle project, using task `gradle build` will output the project into the `lib` directory.

### Local Env

Run:

```shell
$ cd devenv && docker-compose build && docker-compose up -d
```

### Cloud
This is a dockerized application that can be sent to any cloud provider, using the profile `docker` for Spring Boot.

### TODO for v1
- Endpoint to return active or supported services.

### Future of v2
- Better exception management.
- Make generic components for the CRUD instead of for each model.