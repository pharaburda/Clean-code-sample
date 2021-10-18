package com.example.cleancodesample

import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import kotlin.random.Random

class WateringClass {
    fun howOftenWater() {
        val plantsApi = PlantsApi()
        val out = plantsApi.getWateringFrequency("Storczyk")
        val dayOfTheWeek = when (out) {
            1 -> "Monday"
            2 -> "Tuesday"
            3 -> "Wednesday"
            4 -> "Thursday"
            5 -> "Friday"
            6 -> "Saturday"
            7 -> "Sunday"
            else -> ""
        }

        val today = LocalDate.now().dayOfWeek.toString()
        if(today == dayOfTheWeek) {
            displaySnackBar()
        }
    }

    fun displaySnackBar() {
        Snackbar.make(MainActivity().findViewById(R.id.FirstFragment), "You should plant your flower", 10)
    }

}

class PlantsApi {
    fun getWateringFrequency(name: String): Int {
        return Random(2).nextInt(1, 14)
    }
}
