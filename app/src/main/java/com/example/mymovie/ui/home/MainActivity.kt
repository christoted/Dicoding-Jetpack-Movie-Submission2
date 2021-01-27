package com.example.mymovie.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.mymovie.R
import com.example.mymovie.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = viewPagerAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId) {
            R.id.setting -> {
                Toast.makeText(this, "Still on Progress", Toast.LENGTH_SHORT).show()
            } 
        } 
         return true
    }
}