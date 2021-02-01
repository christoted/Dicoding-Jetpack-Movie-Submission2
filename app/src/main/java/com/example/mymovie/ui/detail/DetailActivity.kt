package com.example.mymovie.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.databinding.ActivityDetailBinding
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.viewmodel.ViewModelFactory

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel : DetailViewModel

    companion object {
        val RECEIVE_INTENT_MOVIE = "receive_movies"
        val RECEIVE_INTENT_TVSHOWS = "receive_tvshows"
        val TAG = DetailActivity::class.java.simpleName
    }

    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory.getInstance(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        val extras = intent.extras

        if ( extras != null) {

            binding.progressBar.visibility = View.VISIBLE

            val movie = extras.getParcelable<Movie>(RECEIVE_INTENT_MOVIE)
            val tvShow = extras.getParcelable<TvShow>(RECEIVE_INTENT_TVSHOWS)

            val movieImbdID = movie?.imdbID
            val tvShowImbdID = tvShow?.imdbID

            if ( movie != null) {
                viewModel.setSelectedMovie(movieImbdID ?: "tt2245084")
                viewModel.getMovieSelected().observe(this, Observer {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutDetail.visibility = View.VISIBLE

                    val selectedMovie = it

                    Glide.with(this)
                     //   .load(selectedMovie.Poster)
                 //       .into(binding.imageView)
//
//                    binding.movieTitleDetail.text = selectedMovie.Title
//                    binding.movieReleaseDateDetail.text = selectedMovie.Year
//                    binding.movieAuthorDetail.text = selectedMovie.imdbID
                })


//                val selectedMovie = viewModel.getMovieSelected()
//
//                Glide.with(this)
//                    .load(selectedMovie.Poster)
//                    .into(binding.imageView)
//                binding.movieTitleDetail.text = selectedMovie.Title
//                binding.movieReleaseDateDetail.text = selectedMovie.Year
//                binding.movieAuthorDetail.text = selectedMovie.imdbID

            } else if ( tvShow != null) {
                viewModel.setSelectedTVShow(tvShowImbdID ?: "tt0441773")

                viewModel.getTVShowSelected().observe(this, Observer {
                    binding.progressBar.visibility = View.GONE
                    binding.layoutDetail.visibility = View.VISIBLE

                    val selectedTVShow = it.data

                    if (selectedTVShow != null) {
                        for ( i in selectedTVShow) {
                            if ( i.imdbID == tvShowImbdID) {
                                Glide.with(this)
                                    .load(i.Poster)
                                    .into(binding.imageView)


                            }
                        }
                    }

//                    Glide.with(this)
//                        .load(selectedTVShow.Poster)
//                        .into(binding.imageView)


//                    binding.movieTitleDetail.text = selectedTVShow.Title
//                    binding.movieReleaseDateDetail.text = selectedTVShow.Year
//                    binding.movieAuthorDetail.text = selectedTVShow.imdbID
                })

//                val selectedTVShow = viewModel.getTVShowSelected()
//
//                Glide.with(this)
//                    .load(selectedTVShow.Poster)
//                    .into(binding.imageView)
//                binding.movieTitleDetail.text = selectedTVShow.Title
//                binding.movieReleaseDateDetail.text = selectedTVShow.Year
//                binding.movieAuthorDetail.text = selectedTVShow.imdbID

            }
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}