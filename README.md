# Personalized News Service API

This is a RESTful API built with Spring Boot that provides personalized news aggregation. It features user registration, login with JWT-based authentication, and a news feed endpoint that fetches articles from external sources based on user-defined preferences.

## Features

**Authentication:** Secure registration and login using Spring Security and JSON Web Tokens (JWT).

**Personalization:** Users can save and update their news preferences (keywords and sources).

**Dynamic News Feed:** A dedicated endpoint (/api/news) that fetches news from third-party APIs based on the logged-in user's preferences.

**In-Memory Database:** Uses H2 for easy setup and development.

**Validation:** Input validation for all user-facing endpoints.

**Centralized Error Handling:** Graceful exception handling for validation, authentication, and other runtime errors.

## Technology Stack

**Framework:** Spring Boot 3

**Language:** Java 17

**Security:** Spring Security (with JWT)

**Database:** Spring Data JPA with H2 (In-Memory)

**API Client:** Spring WebFlux (WebClient)

**Validation:** Spring Validation

**Build Tool:** Maven

## Requirements

Java 17 (or newer)

##  Apache Maven

A News API Key from NewsAPI.org (or another provider)

## Getting Started

**1.** Clone the Repository

git clone [https://your-repository-url.git](https://your-repository-url.git)
cd news-service


**2.** Configure Your API Key

Before running the application, you must add your external news API key to the configuration file.

Open src/main/resources/application.properties.

Find the following line and add your key:

newsapi.key=YOUR_ACTUAL_API_KEY_GOES_HERE


**3.** Run the Application

You can run the application using the Spring Boot Maven plugin:

mvn spring-boot:run


The application will start on http://localhost:8080.

You can access the H2 in-memory database console at:
http://localhost:8080/h2-console

Driver Class: org.h2.Driver

JDBC URL: jdbc:h2:mem:newsdb

User Name: sa

Password: password

## API Endpoints

All authenticated endpoints require a Bearer Token in the Authorization header.

Authentication (Public)

### POST /api/register

Registers a new user and returns a JWT.

### Request Body:

{
  "username": "testuser",
  "password": "password123"
}


### Response Body:

{
  "token": "eyJhbGciOiJIZXVzI1NiJ9..."
}


### POST /api/login

Logs in an existing user and returns a JWT.

### Request Body:

{
  "username": "testuser",
  "password": "password123"
}


### Response Body:

{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}


## News Preferences (Authenticated)

### GET /api/preferences

Retrieves the news preferences for the currently logged-in user.

### Response Body:

{
  "keywords": ["java", "spring boot"],
  "sources": ["techcrunch"]
}


### PUT /api/preferences

Updates the news preferences for the currently logged-in user.

### Request Body:

{
  "keywords": ["java", "spring boot", "ai"],
  "sources": ["techcrunch", "wired"]
}

### Response Body:

{
  "keywords": ["java", "spring boot", "ai"],
  "sources": ["techcrunch", "wired"]
}


## News Feed (Authenticated)

### GET /api/news

Fetches news articles from the external NewsAPI based on the user's saved preferences.

### Response Body:
(This is a proxied response from NewsAPI.org)

{
  "status": "ok",
  "totalResults": 1234,
  "articles": [
    {
      "source": { "id": "techcrunch", "name": "TechCrunch" },
      "author": "Author Name",
      "title": "Article about Java",
      "description": "...",
      "url": "...",
      "urlToImage": "...",
      "publishedAt": "2023-11-20T10:00:00Z",
      "content": "..."
    }
  ]
}
