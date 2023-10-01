# MovieRama API

THE API for MovieRama, a movie review application.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Build the Project](#build-the-project)
    - [Run the Application](#run-the-application)
- [API Endpoints](#api-endpoints)

## Prerequisites

Before you begin, ensure you have met the following requirements:

- Java Development Kit (JDK) 8 or higher installed.
- Apache Maven installed (for building the project).
- [Docker](https://www.docker.com/) installed.

## Getting Started

Follow these steps to set up and run the project locally.

### Start the Docker MySQL Database

Before running the application, start the Docker MySQL database using the following command:

```bash
docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=rootPassword -e MYSQL_DATABASE=MovieRama -e MYSQL_USER=adminName -e MYSQL_PASSWORD=adminPassword -d -p 3306:3306 mysql:latest
```

### Clone the Repository

```bash
git clone https://github.com/GiatrGio/MovieRama_API.git
cd MovieRama_API
```

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will be accessible at `http://localhost:8080`.

## API Endpoints

### Movie Endpoints

- Movie Endpoints: Access it at `http://localhost:8080/api/movies/`
    - List all movies: GET `http://localhost:8080/api/movies/`
    - Get movies by user ID: GET `http://localhost:8080/api/movies/{userId}/`
    - Create a new movie: POST `http://localhost:8080/api/movies/`
        - Request Body Format (JSON):
      ```json
      {
        "userId": 123,
        "title": "Movie Title",
        "description": "Movie Description"
      }
      ```

### User Endpoints

- User Endpoints: Access it at `http://localhost:8080/api/users/`
    - Login User: POST `http://localhost:8080/api/users/login`
        - Request Body Format (JSON):
      ```json
      {
        "username": "user123",
        "password": "password123"
      }
      ```
        - Response:
            - Status Code: 200 (OK) - Successful login.
                - Response Body: The user's ID.
            - Status Code: 401 (Unauthorized) - Login failed.
            - Status Code: 400 (Bad Request) - Invalid request.
    - Register User: POST `http://localhost:8080/api/users/register`
        - Request Body Format (JSON):
      ```json
      {
        "username": "newUser",
        "password": "newPassword"
      }
      ```
        - Response:
            - Status Code: 201 (Created) - User registered successfully.
                - Response Body: The new user's ID.
            - Status Code: 400 (Bad Request) - Invalid request.

### Vote Endpoints

- Vote Endpoints: Access it at `http://localhost:8080/api/votes/`
    - Update Vote: POST `http://localhost:8080/api/votes/`
        - Request Body Format (JSON):
      ```json
      {
        "voteId": 123,
        "userId": 456,
        "movieId": 789,
        "voteType": "LIKE"
      }
      ```
        - Response:
            - Status Code: 200 (OK) - Successful vote update.
                - Response Body: "Vote successfully updated"
            - Status Code: 400 (Bad Request) - Invalid request or user votes their own movie.
