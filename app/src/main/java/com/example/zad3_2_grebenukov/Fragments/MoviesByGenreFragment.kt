package com.example.zad3_2_grebenukov.Fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.zad3_2_grebenukov.Activity.MovieDetailsActivity
import com.example.zad3_2_grebenukov.Adapters.MovieAdapter
import com.example.zad3_2_grebenukov.Data.Movie
import com.example.zad3_2_grebenukov.R
import com.example.zad3_2_grebenukov.databinding.FragmentMoviesByGenreBinding
import org.json.JSONObject


class MoviesByGenreFragment : Fragment() {
    private lateinit var binding: FragmentMoviesByGenreBinding
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var requestQueue: RequestQueue

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesByGenreBinding.inflate(inflater, container, false)
        binding.rcMovies.layoutManager = GridLayoutManager(requireContext(), 3)
        movieAdapter = MovieAdapter(requireContext(), emptyList())
        movieAdapter = MovieAdapter(requireContext(), emptyList())
        binding.rcMovies.adapter = movieAdapter

        movieAdapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(requireContext(), MovieDetailsActivity::class.java)
                intent.putExtra("movieTitle", movie.title)
                startActivity(intent)
            }
        })

        val genre = arguments?.getString(ARG_KEY)
        requestQueue = Volley.newRequestQueue(requireContext())

        if (genre != null) {
            fetchMoviesByGenre(genre)
        }
        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                movieAdapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        return binding.root
    }
    private fun fetchMoviesByGenre(genre: String) {
        val key = "6a3586ec"
        val url = "https://www.omdbapi.com/?apikey=${key}&s=${genre}&type=movie"

        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                try {

                    val jsonObject = JSONObject(response)
                    val error = jsonObject.optString("Error")

                    if (error.isNullOrEmpty()) {
                        val searchArray = jsonObject.optJSONArray("Search")

                        if (searchArray != null) {
                            val movies = mutableListOf<Movie>()

                            for (i in 0 until searchArray.length()) {
                                val movieObject = searchArray.optJSONObject(i)
                                val title = movieObject.optString("Title")
                                val description = movieObject.optString("Plot")
                                val posterUrl = movieObject.optString("Poster")

                                movies.add(Movie(title, description, posterUrl))
                            }

                            if (movies.isNotEmpty()) {
                                movies.forEach {
                                    Log.d(
                                        "Movie",
                                        "${it.title}\n${it.description}\n${it.posterUrl}\n"
                                    )
                                }
                                movieAdapter.setMovies(movies)
                            } else {
                                Toast.makeText(requireContext(), "Фильмы не найдены.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e("Volley", "Результаты поиска пустые")
                        }
                    } else {
                        Log.e("Volley", error)
                    }
                } catch (e: Exception) {
                    Log.e("Volley", "Ошибка парсинга: ${e.message}")
                }
            },
            { error ->
                Log.e("Volley", "Error: ${error.message}")
            })

        requestQueue.add(request)
    }
    companion object {
        private const val ARG_KEY = "myKey"
        fun newInstance(yourValue: String): MoviesByGenreFragment {
            val fragment = MoviesByGenreFragment()
            val args = Bundle()
            args.putString(ARG_KEY, yourValue)
            fragment.arguments = args
            return fragment
        }
    }

}


