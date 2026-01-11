# Auth Service

## Overview
Auth Service is a Spring Boot microservice responsible for authentication via Google Sign-In for the RSO project.
It verifies the Google-issued identity tokens and exchanges them for an internally signed JWT access token that ship-service and component-service use to authorize requests and enforce user ownership.

## Responsibilities
* Accept Google Sign-In ID tokens
* Verify token validity with Google
* Resolve or create a local user
* Issue an internally signed JWT access token

## API
The service exposes a REST API to obtain a users token.

### Auth-controller
| Method | Path         | Description                                                                        |
|--------|--------------|------------------------------------------------------------------------------------|
| GET    | /google      | Get the authentication model that maps the internal JWT access token to the owner. |

## Authorization flow
* The frontend performs the Google sign in
* Google returns an id token (OIDC) in JWT format
* The frontend sends the token to auth-service
* Auth-service validates the token and extracts the google user identifier
* Auth-service creates a new JWT for further API requests.


## Database
The database is a PostgreSQL which schema is managed automatically by Hibernate.

## Swagger / OpenAPI
Swagger UI is available at: /swagger-ui/index.html.

## Deployment
The service is part of a cloud-native microservices system deployed on Azure Kubernetes Service (AKS) and integrated via Ingress-NGINX.

### Docker
The service is containerized and published to the GitHub Container Registry (GHCR).

### CI/CD
Continuous integration and continuous deployment (CI/CD) is implemented using GitHub Actions.
#### GitHub Actions pipeline:
* Build
* Test
* Build Docker image
* Push to GHCR
* Trigger infrastructure deployment