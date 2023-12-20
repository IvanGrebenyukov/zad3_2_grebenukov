package com.example.zad3_2_grebenukov.Activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zad3_2_grebenukov.R
import com.example.zad3_2_grebenukov.databinding.ActivityMovieDetailsBinding
import com.squareup.picasso.Picasso

class MovieDetailsActivity : Activity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val movieTitle = intent.getStringExtra("movieTitle")
        requestQueue = Volley.newRequestQueue(this)
        if (!movieTitle.isNullOrEmpty()) {
            getMovieDetails(movieTitle)
        }

    }
    private fun getMovieDetails(title: String) {
        val key = "6a3586ec"
        val url = "https://www.omdbapi.com/?apikey=$key&t=$title"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    Picasso.get()
                        .load(response.optString("Poster"))
                        .placeholder(R.drawable.no_image)
                        .error(R.drawable.no_image)
                        .into(binding.imageViewPosterDetails)
                    binding.textViewTitle.text = title
                    binding.textViewCountryAndRelease.text = "Страна выпуска: ${response.optString("Country")} (${response.optString("Released")})"
                    binding.textViewDescription.text = response.optString("Plot")
                    binding.textViewRating.text = "Оценка: ${response.optString("imdbRating")}"
                    Log.d("Volley",response.toString())

                } catch (e: Exception) {
                    Log.e("Volley", "Ошибка парсинга: ${e.message}")
                }
            },
            { error ->
                Log.e("Volley", ": ${error.message}")
            }
        )

        requestQueue.add(request)
    }
}