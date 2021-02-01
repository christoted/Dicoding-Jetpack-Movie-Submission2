package com.example.mymovie.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.utils.FakeData
import com.example.mymovie.vo.Resource

class TvShowViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    fun getTVShow() : LiveData<Resource<List<TvShow>>> = filmRepository.getAllTVShow()

}