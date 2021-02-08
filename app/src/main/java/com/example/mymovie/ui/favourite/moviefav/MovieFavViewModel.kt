package com.example.mymovie.ui.favourite.moviefav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.vo.Resource

class MovieFavViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getMovieSaved(): LiveData<PagedList<Movie>> = filmRepository.getBookmarkedMovie()
}