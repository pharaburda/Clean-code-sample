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



        val movies =  MoviesAPI().getMoviesList()
        movies.forEach{
            val logToPrint = if(it.title.length <   10) it.title else it.title.dropLast(it.title.length - 10)
            view.findViewById<TextView>(R.id.title).text = logToPrint
            val text = SpannableStringBuilder  (it.description)
            if(    it.description.contains("author")) {
                Log.e("My fragment", "DUPA")
            }
            if   (it.description.contains("authors")) {
                text.setSpan(ForegroundColorSpan(Color.GREEN), it.description.indexOf("author"), "authors".length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            val  information =   UserInfo().getUserId()
            if  (UserSubscriptionAPI().isPremiumUser(information    )) {
                view.findViewById<TextView>(R.id.prize).visibility = View.GONE
            } else view.findViewById<TextView   >(R.id.prize).text = "177787 PLN"


            view.findViewById<TextView> (R.id.description).text = text
            if(it.year   < 2000) return
            view.findViewById<TextView>(R.id.year).text     = it.year.toString()

//            val imageUri = when(it.genre) {
//                Genre.COMEDY ->

            val icon = when(it.genre) {
                Genre.COMEDY ->      "/url/to/comedy/icon"
                Genre.ACTION ->  "/url/to/action/icon"
                Genre.SCIFI ->   "/url/to/scifi/icon"
                Genre.FANTASY -> "/url/to/fantasy/icon"
                Genre.DRAMA ->  "/url/to/drama/icon"
            }.toUri()

            //Log.d("blablab", "/url/to/drama/icon".toUri().toString())
            Log.e("Patka",   icon.toString())
            view.findViewById   <ImageView>(R.id.genre_icon).setImageURI(icon)

            //this is parental mode and show only children movies
            if(it.rating !=  Rating.G ||     it.rating  != Rating.PG) {
                view.findViewById<TextView>     (R.id.title).visibility = View.GONE
                view.findViewById<TextView>(R.id.description).visibility = View.GONE
                view.findViewById<TextView> (R.id.year).visibility = View.GONE
                view.findViewById<ImageView>(   R.id.genre_icon).visibility = View.GONE
            }

        }

        FirebaseDatabase().saveMovies(movies)
    }
}