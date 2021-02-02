package com.example.mymovie.ui.favourite.tvshowfav

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.entity.TvShow

class TVShowFavViewModel(private val filmRepository: FilmRepository) : ViewModel() {
    fun getSavedTVShow(): LiveData<List<TvShow>> = filmRepository.getBookmarkedTVShow()
}