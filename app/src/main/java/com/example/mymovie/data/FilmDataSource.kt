package com.example.mymovie.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.vo.Resource

interface FilmDataSource {
    //Pagination
    fun getAllMovie() : LiveData<Resource<PagedList<Movie>>>

    //Pagination
    fun getAllTVShow() : LiveData<Resource<PagedList<TvShow>>>

    fun getSelectedMovie(imdbID : String) : LiveData<Resource<Movie>>

    fun getSelectedTVShow(imdbID: String) : LiveData<Resource<TvShow>>

    fun setBookmarkedMovie(movie: Movie, newState: Boolean)

    fun setBookmarkedTVShow(tvShow: TvShow, newState: Boolean)

    //Pagination
    fun getBookmarkedTVShow(): LiveData<PagedList<TvShow>>
    //Pagination
    fun getBookmarkedMovie(): LiveData<PagedList<Movie>>
}