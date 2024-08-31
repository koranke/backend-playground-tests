# BACKEND PLAYGROUND TESTS
This is a companion project to the [Backend Playground](https://github.com/koranke/backend-playground) project. 
It contains system-level tests for the backend playground project, which can be triggered by a CI/CD pipeline for
automated inclusion of typical QA testing of backend services.

This project is intended to be used for testing and demonstration purposes only.  In addition to being used for testing 
GitHub Actions, it also demonstrates different patterns for handling API testing.

To run tests locally, first check out the Backend Playground project and follow instructions there to build and deploy to Docker.
You will need to also add environment variables for the desired DB_USER and DB_PASSWORD values.  Once the application is deployed,
you can run these tests directly in your IDE.
