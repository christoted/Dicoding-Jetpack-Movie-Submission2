package com.example.mymovie.ui.detail

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.databinding.ActivityDetailCollapseBinding
import com.example.mymovie.databinding.ContentScrollingBinding
import com.example.mymovie.viewmodel.ViewModelFactory
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
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = title
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val viewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        activityDetailCollapseBinding.appBar.visibility = View.GONE
        activityDetailCollapseBinding.content.scrollingContent.visibility = View.GONE
        activityDetailCollapseBinding.progressBar.visibility = View.VISIBLE


        val extras = intent.extras

        if (extras != null) {

            val movie = extras.getParcelable<Movie>(DetailActivity.RECEIVE_INTENT_MOVIE)
            val tvShow = extras.getParcelable<TvShow>(DetailActivity.RECEIVE_INTENT_TVSHOWS)

            val movieImbdID = movie?.imdbID
            val tvShowImbdID = tvShow?.imdbID

            if (movie != null) {
                viewModel.setSelectedMovie(movieImbdID ?: "tt2245084")
                viewModel.getMovieSelected().observe(this, Observer {
                    activityDetailCollapseBinding.progressBar.visibility = View.GONE
                    activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                    activityDetailCollapseBinding.content.scrollingContent.visibility = View.VISIBLE
                    val selectedMovie = it.data

                    if ( selectedMovie != null) {
                        for ( i in selectedMovie) {
                            if ( i.imdbID == movieImbdID) {
                                Glide.with(this)
                                    .load(i.Poster)
                                    .into(activityDetailCollapseBinding.imageView)

                                activityDetailCollapseBinding.appBar.background

                                contentScrollingBinding.movieTitleDetail.text = i.Title
                                contentScrollingBinding.movieReleaseDateDetail.text = i.Year
                                contentScrollingBinding.movieAuthorDetail.text = i.imdbID
                                break
                            }
                        }
                    }


                })
            } else if (tvShow != null) {

                activityDetailCollapseBinding.progressBar.visibility = View.GONE
                activityDetailCollapseBinding.appBar.visibility = View.VISIBLE
                activityDetailCollapseBinding.content.scrollingContent.visibility = View.VISIBLE

                viewModel.setSelectedTVShow(tvShowImbdID ?: "tt0441773")

                viewModel.getTVShowSelected().observe(this, Observer {
                    activityDetailCollapseBinding.progressBar.visibility = View.GONE
                    activityDetailCollapseBinding.layoutDetailCollapse.visibility = View.VISIBLE

                    val selectedTVShow = it.data

                    if ( selectedTVShow != null) {
                        for ( i in selectedTVShow) {
                            if ( i.imdbID == tvShowImbdID ) {
                                Glide.with(this)
                                    .load(i.Poster)
                                    .into(activityDetailCollapseBinding.imageView)

                                contentScrollingBinding.movieTitleDetail.text = i.Title
                                contentScrollingBinding.movieReleaseDateDetail.text = i.Year
                                contentScrollingBinding.movieAuthorDetail.text = i.imdbID
                                break
                            }
                        }

                    }


                })
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



}