package com.example.mymovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.entity.Movie
import com.example.mymovie.data.entity.TvShow
import com.example.mymovie.utils.FakeData

class DetailViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private lateinit var imbdID : String

    fun setSelectedMovie(imbdID : String){
        this.imbdID = imbdID
    }

    fun setSelectedTVShow(imbdID: String) {
        this.imbdID = imbdID
    }

    fun getMovieSelected() : LiveData<Movie> = filmRepository.getSelectedMovie(imbdID)

    fun getTVShowSelected(): LiveData<TvShow> = filmRepository.getSelectedTVShow(imbdID)

}