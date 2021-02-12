package com.example.mymovie.ui.detail

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.databinding.ActivityDetailCollapseBinding
import com.example.mymovie.databinding.ContentScrollingBinding
import com.example.mymovie.viewmodel.ViewModelFactory
import com.example.mymovie.vo.Resource
import com.example.mymovie.vo.Status
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class DetailCollapseActivity : AppCompatActivity() {

    private lateinit var activityDetailCollapseBinding: ActivityDetailCollapseBinding
    private lateinit var contentScrollingBinding: ContentScrollingBinding

    private lateinit var viewModel: DetailViewModel

    companion object {
        val RECEIVE_INTENT_MOVIE = "receive_movies"
        val RECEIVE_INTENT_TVSHOWS = "receive_tvshows"
        val TAG = DetailActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailCollapseBinding = ActivityDetailCollapseBinding.inflate(layoutInflater)
        contentScrollingBinding = activityDetailCollapseBinding.content
        setContentView(activityDetailCollapseBinding.root)
        
        setSupportActionBar(findViewById(R.id.toolbar))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val viewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]


        val extras = intent.extras

        if (extras != null) {

            val movie = extras.getParcelable<Movie>(DetailActivity.RECEIVE_INTENT_MOVIE)
            val tvShow = extras.getParcelable<TvShow>(DetailActivity.RECEIVE_INTENT_TVSHOWS)

            val movieImbdID = movie?.imdbID
            val tvShowImbdID = tvShow?.imdbID

            if (movie != null) {
                viewModel.setSelectedMovie(movieImbdID ?: "tt2245084")
                viewModel.getMovieSelected().observe(this, Observer {
                    when (it.status) {
                        Status.SUCCESS -> {
                            activityDetailCollapseBinding.progressBar.visibility = View.GONE
                            activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.VISIBLE

                            val selectedMovie = it.data
                            Glide.with(this)
                                .load(selectedMovie?.Poster)
                                .into(activityDetailCollapseBinding.imageView)

                            contentScrollingBinding.movieTitleDetail.text = selectedMovie?.Title
                            contentScrollingBinding.movieReleaseDateDetail.text =
                                selectedMovie?.Year
                            contentScrollingBinding.movieAuthorDetail.text = selectedMovie?.imdbID

                            if (selectedMovie != null) {
                                setBookmarkState(selectedMovie.bookmarked)
                            }
                        }

                        Status.LOADING -> {
                            activityDetailCollapseBinding.appBar.visibility = View.GONE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.GONE
                            activityDetailCollapseBinding.progressBar.visibility = View.VISIBLE
                        }

                        Status.ERROR -> {
                            activityDetailCollapseBinding.progressBar.visibility = View.GONE
                            activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.VISIBLE

                            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
                        }
                    }

                })

                activityDetailCollapseBinding.fab.setOnClickListener {
                        viewModel.setBookMarkedMovie(movie)
                }



            } else if (tvShow != null) {

                viewModel.setSelectedTVShow(tvShowImbdID ?: "tt0441773")

                viewModel.getTVShowSelected().observe(this, Observer {

                    when (it.status) {
                        Status.SUCCESS -> {
                            activityDetailCollapseBinding.progressBar.visibility = View.GONE
                            activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.VISIBLE

                            val selectedTVShow = it.data
                            Glide.with(this)
                                .load(selectedTVShow?.Poster)
                                .into(activityDetailCollapseBinding.imageView)

                            contentScrollingBinding.movieTitleDetail.text = selectedTVShow?.Title
                            contentScrollingBinding.movieReleaseDateDetail.text = selectedTVShow?.Year
                            contentScrollingBinding.movieAuthorDetail.text = selectedTVShow?.imdbID

                            if (selectedTVShow != null) {
                                setBookmarkState(selectedTVShow.bookmarked)
                            }
                        }

                        Status.ERROR -> {
                            activityDetailCollapseBinding.progressBar.visibility = View.GONE
                            activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.VISIBLE

                            Toast.makeText(this, "Error Occurred", Toast.LENGTH_SHORT).show()
                        }

                        Status.LOADING -> {
                            activityDetailCollapseBinding.appBar.visibility = View.GONE
                            activityDetailCollapseBinding.content.scrollingContent.visibility =
                                View.GONE
                            activityDetailCollapseBinding.progressBar.visibility = View.VISIBLE
                        }
                    }

                })

                activityDetailCollapseBinding.fab.setOnClickListener {
                    viewModel.setBookMarkedTVShow(tvShow)
                }
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w: Window = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_detail_collapse, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        
        when(item.itemId) {
            R.id.action_settings -> {
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show()
            }
        }
        
        return super.onOptionsItemSelected(item)
    }


    private fun setBookmarkState(state: Boolean) {
        if (state) {
            activityDetailCollapseBinding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            activityDetailCollapseBinding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

}

