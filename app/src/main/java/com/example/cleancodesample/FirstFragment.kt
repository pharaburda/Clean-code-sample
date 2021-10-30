package com.example.cleancodesample

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

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
        }

        fun bindTitle(movie: Movie) {
            titleView.text = getMovieTitle(movie.title)
        }

        fun bindDescription(movie: Movie) {
            descriptionView.text = getMovieDescription(movie.description)
        }

        fun bindPrize() {
            val userId = UserInfo().getUserId()
            if (UserSubscriptionAPI().isPremiumUser(userId)) {
                prizeView.visibility = View.GONE
            } else {
                prizeView.visibility = View.VISIBLE
                prizeView.text = "177787 PLN"
            }
        }

        fun bindYear(movie: Movie) {
            if (movie.year < 2000) {
                yearView.visibility = View.GONE
            } else {
                yearView.visibility = View.VISIBLE
                yearView.text = movie.year.toString()
            }
        }

        fun bindIcon(movie: Movie) {
            val icon = when (movie.genre) {
                Genre.COMEDY -> "/url/to/comedy/icon"
                Genre.ACTION -> "/url/to/action/icon"
                Genre.SCIFI -> "/url/to/scifi/icon"
                Genre.FANTASY -> "/url/to/fantasy/icon"
                Genre.DRAMA -> "/url/to/drama/icon"
            }.toUri()

            genreIcon.setImageURI(icon)
        }

        val movies = MoviesAPI().getMoviesList()
        movies.forEach { movie ->
            if (movie.isNotSuitableForChildren()) {
                hide()
            }
            bindTitle(movie)
            bindDescription(movie)
            bindPrize()
            bindYear(movie)
            bindIcon(movie)
        }

        FirebaseDatabase().saveMovies(movies)
    }

    private fun Movie.isNotSuitableForChildren(): Boolean =
        this.rating != Rating.G || this.rating != Rating.PG

    private fun getMovieTitle(title: String): String {
        return if (title.length < 10) {
            title
        } else {
            title.dropLast(title.length - 10)
        }
    }

    private fun getMovieDescription(description: String): SpannableStringBuilder {
        val movieDescription = SpannableStringBuilder(description)
        if (description.contains("authors")) {
            movieDescription.setSpan(
                ForegroundColorSpan(Color.GREEN),
                description.indexOf("author"),
                "authors".length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return movieDescription
    }
}