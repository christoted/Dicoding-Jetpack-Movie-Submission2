package com.example.mymovie.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.entity.TvShow
import com.example.mymovie.utils.FakeData

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getTVShow() : LiveData<List<TvShow>> = filmRepository.getAllTVShow()

}