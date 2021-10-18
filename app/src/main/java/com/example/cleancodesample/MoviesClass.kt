package com.example.cleancodesample

import java.util.*


class MoviesClass {

}

class MoviesAPI {
    fun getMoviesList(): List<Movie> {
        return listOf(
            Movie("Goldeneye", "James Bond movie", 1995, Genre.ACTION, Rating.PG13),
            Movie("Shrek", "A movie about Shrek", 2001, Genre.COMEDY, Rating.G)
        )
    }
}

class UserInfo() {
    fun getUserId(): String = UUID.randomUUID().toString()
}

class UserSubscriptionAPI() {
    fun isPremiumUser(userId: String): Boolean {
        return true
    }
}

class Movie(
    val title: String,
    val description: String,
    val year: Int,
    val genre: Genre,
    val rating: Rating
)

enum class Genre {
    ACTION,
    COMEDY,
    SCIFI,
    FANTASY,
    DRAMA
}

enum class Rating {
    G, PG, PG13, R, A
}

class FirebaseDatabase() {
    fun saveMovies(movies: List<Movie>) {

    }
}
