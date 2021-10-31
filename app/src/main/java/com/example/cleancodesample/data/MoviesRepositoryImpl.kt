package com.example.cleancodesample.data

import com.example.cleancodesample.data.entities.Genre
import com.example.cleancodesample.data.entities.Movie
import com.example.cleancodesample.data.entities.Rating
import com.example.cleancodesample.domain.MoviesRepository

class MoviesRepositoryImpl: MoviesRepository {
    override fun getMoviesList(): List<Movie> {
        return listOf(
            Movie("Goldeneye", "James Bond movie", 1995, Genre.ACTION, Rating.PG13),
            Movie("Shrek", "A movie about Shrek", 2001, Genre.COMEDY, Rating.G)
        )
    }

    override fun saveMovies(movies: List<Movie>) {
        // perform save in database or remote server
    }
}