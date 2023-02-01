
# Song Society

A social media website for music lovers, built with JHipster, React, Spring Boot, and MySQL.

## Requirements

-   Java 11 or later
-   Node.js and npm
-   Yarn
-   MySQL or another database of your choice

## Getting Started

1.  Clone the repository: `git clone https://github.com/<your-username>/ExtraMinty.git`
2.  Navigate to the project directory: `cd ExtraMinty`
3.  Install the backend dependencies: `./mvnw clean install`
4.  Install the frontend dependencies: `yarn install`
5.  Start the development server: `./mvnw` and `yarn start`
6.  Visit [http://localhost:3000](http://localhost:3000/) in your browser to see the application.

## Features

-   User authentication with JWT
-   User profiles with music-related information
-   A feed for users to post songs and other things they find interesting about music
-   The ability to like and reply to posts
-   A follower system, allowing users to follow each other
-   An embedded player powered by the Spotify API
-   Real-time user stats on profile pages, also powered by the Spotify API

## Deployment

To deploy the application, you can build a production-ready version of the frontend using the following command: `yarn build`. Then, you can use the following command to build and run the backend: `./mvnw -Pprod clean package && java -jar target/*.war`.

## Acknowledgements

-   JHipster for providing a generator for quickly creating a Spring Boot and React application.
-   Spotify API for providing access to user stats and an embedded player.

## Contributing

If you're interested in contributing to the project, feel free to submit a pull request or open an issue for any bugs or suggestions.
