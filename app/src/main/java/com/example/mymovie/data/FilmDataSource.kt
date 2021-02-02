package com.example.mymovie.data

import androidx.lifecycle.LiveData
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.vo.Resource

interface FilmDataSource {
    fun getAllMovie() : LiveData<Resource<List<Movie>>>

    fun getAllTVShow() : LiveData<Resource<List<TvShow>>>

    fun getSelectedMovie(imdbID : String) : LiveData<Resource<Movie>>

    fun getSelectedTVShow(imdbID: String) : LiveData<Resource<TvShow>>

    fun setBookmarkedMovie(movie: Movie, newState: Boolean)

    fun setBookmarkedTVShow(tvShow: TvShow, newState: Boolean)

    fun getBookmarkedTVShow(): LiveData<List<TvShow>>

    fun getBookmarkedMovie(): LiveData<List<Movie>>
}