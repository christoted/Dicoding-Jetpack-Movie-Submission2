package com.example.mymovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.entity.Movie
import com.example.mymovie.utils.FakeData

class MovieViewModel(private val filmRepository: FilmRepository) : ViewModel(){

    fun getMovie() : LiveData<List<Movie>> = filmRepository.getAllMovie()

}