package com.example.cleancodesample.presentation

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.example.cleancodesample.R
import com.example.cleancodesample.data.entities.Genre
import com.example.cleancodesample.data.entities.Movie
import com.example.cleancodesample.data.entities.Rating
import com.example.cleancodesample.domain.MoviesRepository
import com.example.cleancodesample.domain.UserRepository
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private val moviesRepository: MoviesRepository by inject()
    private val userRepository: UserRepository by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleView = view.findViewById<TextView>(R.id.title)
        val descriptionView = view.findViewById<TextView>(R.id.description)
        val yearView = view.findViewById<TextView>(R.id.year)
        val genreIcon = view.findViewById<ImageView>(R.id.genre_icon)
        val prizeView = view.findViewById<TextView>(R.id.prize)

        fun hide() {
            titleView.visibility = View.GONE
            descriptionView.visibility = View.GONE
            yearView.visibility = View.GONE
            genreIcon.visibility = View.GONE
            prizeView.visibility = View.GONE
        }

        fun bindTitle(movie: Movie) {
            titleView.text = getMovieTitle(movie.title)
        }

        fun bindDescription(movie: Movie) {
            descriptionView.text = getMovieDescription(movie.description)
        }

        fun bindPrize() {
            val userId = userRepository.getUserId()
            if (userRepository.isPremiumUser(userId)) {
                prizeView.visibility = View.GONE
            } else {
                prizeView.visibility = View.VISIBLE
                prizeView.text = MOVIE_PRIZE
            }
        }

        fun bindYear(movie: Movie) {
            if (movie.year < MAX_MOVIE_YEAR) {
                yearView.visibility = View.GONE
            } else {
                yearView.visibility = View.VISIBLE
                yearView.text = movie.year.toString()
            }
        }

        fun bindIcon(movie: Movie) {
            val icon = when (movie.genre) {
                Genre.COMEDY -> COMEDY_URL
                Genre.ACTION -> ACTION_URL
                Genre.SCIFI -> SCIFI_URL
                Genre.FANTASY -> FANTASY_URL
                Genre.DRAMA -> DRAMA_URL
            }.toUri()

            genreIcon.setImageURI(icon)
        }

        getMovies().forEach { movie ->
            if (movie.isNotSuitableForChildren()) {
                hide()
            }
            bindTitle(movie)
            bindDescription(movie)
            bindPrize()
            bindYear(movie)
            bindIcon(movie)
        }

    }

    private fun getMovies(): List<Movie> {
        return moviesRepository.getMoviesList()
    }

    fun saveMovies(movies: List<Movie>) {
        moviesRepository.saveMovies(movies)
    }

    private fun Movie.isNotSuitableForChildren(): Boolean =
        this.rating != Rating.G || this.rating != Rating.PG

    private fun getMovieTitle(title: String): String {
        return if (title.length < MAX_TITLE_LENGTH) {
            title
        } else {
            title.dropLast(title.length - MAX_TITLE_LENGTH)
        }
    }

    private fun getMovieDescription(description: String): SpannableStringBuilder {
        val movieDescription = SpannableStringBuilder(description)
        if (description.contains(AUTHORS)) {
            movieDescription.setSpan(
                ForegroundColorSpan(Color.GREEN),
                description.indexOf(AUTHORS),
                AUTHORS.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return movieDescription
    }

    companion object {
        const val MOVIE_PRIZE = "177787 PLN"
        const val AUTHORS = "authors"
        const val COMEDY_URL = "/url/to/comedy/icon"
        const val ACTION_URL = "/url/to/action/icon"
        const val SCIFI_URL = "/url/to/scifi/icon"
        const val FANTASY_URL = "/url/to/fantasy/icon"
        const val DRAMA_URL = "/url/to/drama/icon"
        const val MAX_MOVIE_YEAR = 2000
        const val MAX_TITLE_LENGTH = 10
    }
}