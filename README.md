# BACKEND PLAYGROUND TESTS

[![Build](https://github.com/koranke/backend-playground-tests/actions/workflows/build.yml/badge.svg)](https://github.com/koranke/backend-playground-tests/actions/workflows/build.yml)

This is a companion project to the [Backend Playground](https://github.com/koranke/backend-playground) project. 
It contains system-level tests for the backend playground project, which can be triggered by a CI/CD pipeline for
automated inclusion of typical QA testing of backend services.

This project is intended to be used for testing and demonstration purposes only.  In addition to being used for testing 
GitHub Actions, it also demonstrates different patterns for handling API testing.

To run tests locally, first check out the Backend Playground project and follow instructions there to build and deploy to Docker.
You will need to also add environment variables for the desired DB_USER and DB_PASSWORD values.  Once the application is deployed,
you can run these tests directly in your IDE, or through the command line using the following command:

```shell
mvn clean test
```

## Features
* TestNG for test execution and reporting.
* Logging with Log4j.  Each run generates a new log file in the `logs` directory.  Prior logs are archived.
Logs older than 3 days are automatically cleaned up.
* Configuration management with the "Owner" library.  This library allows for easy reference and management of configuration properties.
* Database access with MyBatis, including Db classes for grouping database access methods per data entity.
* API wrapper classes for easy, flexible access to API endpoints.  Includes "call" and "tryCall" variations for each endpoint,
allowing for simpler calling of endpoints expected to succeed and more complex handling of endpoints that may return errors.
* Data scenario classes for easy setup of test data and verification of expected results.