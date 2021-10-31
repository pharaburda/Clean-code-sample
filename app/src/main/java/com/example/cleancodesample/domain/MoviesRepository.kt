package com.example.cleancodesample.domain

import com.example.cleancodesample.data.entities.Movie

interface MoviesRepository {
    fun getMoviesList(): List<Movie>
    fun saveMovies(movies: List<Movie>)
}