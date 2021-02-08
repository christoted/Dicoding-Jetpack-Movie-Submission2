package com.example.mymovie.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.utils.FakeData
import com.example.mymovie.vo.Resource

class MovieViewModel(private val filmRepository: FilmRepository) : ViewModel(){

    fun getMovie() : LiveData<Resource<PagedList<Movie>>> = filmRepository.getAllMovie()

}