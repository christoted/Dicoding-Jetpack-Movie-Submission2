package com.example.mymovie.data.local

import androidx.lifecycle.LiveData
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.data.local.room.CatalogueDao

class LocalDataSource private constructor(private val catalogueDao: CatalogueDao) {
    companion object {
        private var INSTANCE: LocalDataSource? = null
        fun getInstance(catalogueDao: CatalogueDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(catalogueDao)
    }

    // Movie
    fun getAllMovies(): LiveData<List<Movie>> = catalogueDao.getAllMovies()

    fun insertMovie(listMovie: List<Movie>) = catalogueDao.insertMovie(listMovie)

    fun setBookmarkedMovie(movie: Movie, newState: Boolean) {
        movie.bookmarked = newState
        catalogueDao.updateBookmarkedMovie(movie)
    }

    fun getBookmarkedMovie(): LiveData<List<Movie>> = catalogueDao.getBookmarkedMovie()

    fun getMovieByImbdID(imbdid: String): LiveData<Movie> = catalogueDao.getMovieByImbdID(imbdid)

    fun deleteMovie(movie: Movie) {
        catalogueDao.deleteMovie(movie)
    }

    // TV Show
    fun getAllTVShow(): LiveData<List<TvShow>> = catalogueDao.getAllTVShow()

    fun insertTVShow(listTvShow: List<TvShow>) = catalogueDao.insertTVShow(listTvShow)

    fun setBookmarkedTVShow(tvShow: TvShow, newState: Boolean) {
        tvShow.bookmarked = newState
        catalogueDao.updateBookmarkedTVShow(tvShow)
    }

    fun getBookmarkedTVShow(): LiveData<List<TvShow>> = catalogueDao.getBookmarkedTVshow()

    fun getTVShowByImbdID(imbdid: String): LiveData<TvShow> = catalogueDao.getTVShowByImbdID(imbdid)

    fun deleteTVShow(tvShow: TvShow) {
        catalogueDao.deleteTVShow(tvShow)
    }
}