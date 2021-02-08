package com.example.mymovie.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.di.Injection
import com.example.mymovie.ui.detail.DetailViewModel
import com.example.mymovie.ui.favourite.moviefav.MovieFavViewModel
import com.example.mymovie.ui.favourite.tvshowfav.TVShowFavViewModel
import com.example.mymovie.ui.movie.MovieViewModel
import com.example.mymovie.ui.tvshow.TVShowAdapter
import com.example.mymovie.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val filmRepository: FilmRepository) : ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: ViewModelFactory?= null

        fun getInstance(context: Context) : ViewModelFactory =
            instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }

    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(filmRepository) as T
            }

            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(filmRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                return DetailViewModel(filmRepository) as T
            }

            modelClass.isAssignableFrom(MovieFavViewModel::class.java) -> {
                return MovieFavViewModel(filmRepository) as T
            }

            modelClass.isAssignableFrom(TVShowFavViewModel::class.java) -> {
                return TVShowFavViewModel(filmRepository) as T
            }

            else -> throw Throwable("Uknown View Model")
        }
    }

}