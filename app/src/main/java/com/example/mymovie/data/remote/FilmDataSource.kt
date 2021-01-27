package com.example.mymovie.data.remote

import androidx.lifecycle.LiveData
import com.example.mymovie.data.entity.Movie
import com.example.mymovie.data.entity.TvShow

interface FilmDataSource {
    fun getAllMovie() : LiveData<List<Movie>>

    fun getAllTVShow() : LiveData<List<TvShow>>

    fun getSelectedMovie(imdbID : String) : LiveData<Movie>

    fun getSelectedTVShow(imdbID: String) : LiveData<TvShow>
}