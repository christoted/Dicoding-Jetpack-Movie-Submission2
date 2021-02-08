package com.example.mymovie.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mymovie.R
import com.example.mymovie.databinding.ActivityMainBinding
import com.example.mymovie.ui.favourite.FavouriteFragment
import com.example.mymovie.ui.movie.MovieFragment
import com.example.mymovie.ui.tvshow.TvShowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //     supportActionBar?.elevation = 0f
        val movieFragment = MovieFragment()
        setCurrentFragment(movieFragment)

        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> {
                    val movieFragment = MovieFragment()
                    setCurrentFragment(movieFragment)
                }

                R.id.tvShow -> {
                    val tvShowFragment = TvShowFragment()
                    setCurrentFragment(tvShowFragment)
                }

                R.id.favourite -> {
                    val favouriteFragment = FavouriteFragment()
                    setCurrentFragment(favouriteFragment)
                }
            }
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            addToBackStack(null)
            commit()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when (item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "Still on Progress", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }
}