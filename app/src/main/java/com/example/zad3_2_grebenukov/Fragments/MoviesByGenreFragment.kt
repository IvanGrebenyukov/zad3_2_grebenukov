package com.example.zad3_2_grebenukov.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zad3_2_grebenukov.R


class MoviesByGenreFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_by_genre, container, false)
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