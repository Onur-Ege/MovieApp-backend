# 🎬 Movie App - Backend

This is the backend of a full-stack **Movie Recommendation System** that leverages **graph-based algorithms**, **machine learning**, and **user interactions** to provide personalized movie suggestions.

The project follows the **Model-View-Controller (MVC)** architectural pattern for clean separation of concerns:
- **Model**: Neo4j graph entities and data transfer objects
- **View**: Not applicable (API backend only) [kotlin app](https://github.com/Onurege00/MovieApp)
- **Controller**: Spring REST controllers handle incoming requests and route them to services
- **Service Layer**: Business logic, data aggregation, and processing
- **Repository Layer**: Custom Cypher queries and Neo4j interaction

## 🧠 Key Features

- 🔐 **User Authentication**
  - Firebase Authentication (Google Sign-In)
  - Neo4j user nodes with UID-based identity
- ⭐ **User Interactions**
  - Star-based movie ratings (1-5)
  - Favorites and personalized movie lists
- 🧩 **Recommendation Engine**
  - **User-based recommendations** (Collaborative Filtering)
  - **Movie-based recommendations** using:
    - Graph Data Science (Node2Vec, PageRank)
    - Machine Learning models (e.g., Random Forest)
- 🌐 **Movie Metadata**
  - Real-time data fetching from [TMDb API](https://www.themoviedb.org/documentation/api)
  - Cleaned and formatted movie details
- 🌲 **Graph Database**
  - Neo4j with over 270k movies and millions of relationships
  - Graph-based similarity relationships, and more ...

---

## 📡 REST API Endpoints

| Method | Endpoint                                         | Description                                      |
|--------|--------------------------------------------------|--------------------------------------------------|
| GET    | `/api/movies/favorites?userId={str}`            | List favorite movies for a user                  |
| GET    | `/api/movies/{movieId}`                         | Get details for a specific movie                 |
| POST   | `/api/rating/{rating}`                          | Rate a movie (requires user and movie in body)   |
| GET    | `/api/rating?userId={str}&tmdbId={int}`         | Get the rating of a selected movie to fill stars |
| GET    | `/api/tmdb/popular`                             | Fetch popular movies                             |
| GET    | `/api/tmdb/favorites`                           | Fetch favorite movies (via TMDb account, if any) |
| GET    | `/api/tmdb/discover`                            | Discover movies (filtered from TMDb)             |
| GET    | `/api/recommendations/{userId}`                 | Fetch recommendations based on the user          |
| GET    | `/api/recommendations/{movieId}`                | Fetch recommendations based on the selected movie |

---

## 📊 AOP: Logging & Performance Monitoring

We use **Spring AOP (Aspect-Oriented Programming)** to log and monitor:

- ✅ Request method execution times
- ✅ executed methods logs to debug easily
- ✅ Performance of service methods

All major service and controller methods are wrapped using custom AOP aspects, improving visibility and diagnostics.

---

## 🛡️ Authentication & Security

All endpoints are protected. Each request must include bearer token<firebase-id-token>
