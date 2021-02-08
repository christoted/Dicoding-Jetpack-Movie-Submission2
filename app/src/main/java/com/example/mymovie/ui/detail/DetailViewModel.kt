package com.example.mymovie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.vo.Resource

class DetailViewModel(private val filmRepository: FilmRepository) : ViewModel() {

    private lateinit var imbdID: String

    fun setSelectedMovie(imbdID: String) {
        this.imbdID = imbdID
    }

    fun setSelectedTVShow(imbdID: String) {
        this.imbdID = imbdID
    }

    fun setBookMarkedMovie(movie: Movie) {
        val newState = !movie.bookmarked
        filmRepository.setBookmarkedMovie(movie, newState)
    }

    fun setBookMarkedTVShow(tvShow: TvShow) {
        val newState = !tvShow.bookmarked
        filmRepository.setBookmarkedTVShow(tvShow, newState)
    }

    fun setBookMarkedMovie() {
        val movieResource = filmRepository.getSelectedMovie(imbdID).value
        if (movieResource != null) {
            val movie = movieResource.data

            if (movie != null) {
                val newState = !movie.bookmarked
                filmRepository.setBookmarkedMovie(movie, newState)
            }
        }
    }

    fun setBookMarkedTVShow() {
        val tVShowResource =  filmRepository.getSelectedTVShow(imbdID).value
        if (tVShowResource != null) {
            val tvShow = tVShowResource.data
            if ( tvShow != null) {
                val newState = !tvShow.bookmarked
                filmRepository.setBookmarkedTVShow(tvShow, newState)
            }
        }
    }

    fun getMovieSelected(): LiveData<Resource<Movie>> {

        return filmRepository.getSelectedMovie(imbdID)
    }

    fun getTVShowSelected(): LiveData<Resource<TvShow>> {
        return filmRepository.getSelectedTVShow(imbdID)
    }

}