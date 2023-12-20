package com.example.zad3_2_grebenukov


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.zad3_2_grebenukov.Fragments.MoviesByGenreFragment
import com.example.zad3_2_grebenukov.Fragments.NewsFragment
import com.example.zad3_2_grebenukov.databinding.ActivityQuestsBinding


class QuestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.news -> {
                    loadFragment(NewsFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action -> {
                    val selectedValue = "action"
                    val fragment = MoviesByGenreFragment.newInstance(selectedValue)
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.comedy -> {
                    val selectedValue = "comedy"
                    val fragment = MoviesByGenreFragment.newInstance(selectedValue)
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.horror -> {
                    val selectedValue = "horror"
                    val fragment = MoviesByGenreFragment.newInstance(selectedValue)
                    loadFragment(fragment)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


        loadFragment(NewsFragment())
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}