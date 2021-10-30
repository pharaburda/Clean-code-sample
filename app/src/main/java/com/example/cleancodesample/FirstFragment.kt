package com.example.cleancodesample

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import java.lang.NullPointerException
import java.time.DateTimeException
import java.util.*

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

        val movies = MoviesAPI().getMoviesList()
        movies.forEach {
            val logToPrint =
                if (it.title.length < 10) it.title else it.title.dropLast(it.title.length - 10)
            titleView.text = logToPrint
            val text = SpannableStringBuilder(it.description)
            if (it.description.contains("authors")) {
                text.setSpan(
                    ForegroundColorSpan(Color.GREEN),
                    it.description.indexOf("author"),
                    "authors".length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            val information = UserInfo().getUserId()
            if (UserSubscriptionAPI().isPremiumUser(information)) {
                prizeView.visibility = View.GONE
            } else prizeView.text = "177787 PLN"


            descriptionView.text = text
            if (it.year < 2000) return
            yearView.text = it.year.toString()

            val icon = when (it.genre) {
                Genre.COMEDY -> "/url/to/comedy/icon"
                Genre.ACTION -> "/url/to/action/icon"
                Genre.SCIFI -> "/url/to/scifi/icon"
                Genre.FANTASY -> "/url/to/fantasy/icon"
                Genre.DRAMA -> "/url/to/drama/icon"
            }.toUri()

            genreIcon.setImageURI(icon)

            if (it.isNotSuitableForChildren()) {
                hide()
            }
        }

        FirebaseDatabase().saveMovies(movies)
    }

    private fun Movie.isNotSuitableForChildren(): Boolean = this.rating != Rating.G || this.rating != Rating.PG


}