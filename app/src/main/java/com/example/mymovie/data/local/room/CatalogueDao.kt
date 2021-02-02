package com.example.mymovie.data.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow

@Dao
interface CatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(listMovie : List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShow(listTvShow: List<TvShow>)

    // Pagination
    @Query("SELECT * FROM movieentities")
    fun getAllMovies(): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movieentities WHERE imbdid = :imbdid")
    fun getMovieByImbdID(imbdid: String) : LiveData<Movie>

    // Pagination
    @Query("SELECT * FROM movieentities WHERE bookmarked = 1")
    fun getBookmarkedMovie(): DataSource.Factory<Int, Movie>

    // Pagination
    @Query("SELECT * FROM tvshowentities")
    fun getAllTVShow(): DataSource.Factory<Int, TvShow>

    @Query("SELECT * FROM tvshowentities WHERE imbdid = :imbdid")
    fun getTVShowByImbdID(imbdid: String): LiveData<TvShow>

    // Pagination
    @Query("SELECT * FROM tvshowentities WHERE bookmarked = 1")
    fun getBookmarkedTVshow(): DataSource.Factory<Int, TvShow>

    // For update state Bookmark
    @Update
    fun updateBookmarkedTVShow(tvShow: TvShow)

    @Update
    fun updateBookmarkedMovie(movie: Movie)

    // Delete if necessary
    @Delete()
    fun deleteMovie(movie: Movie)

    @Delete
    fun deleteTVShow(tvShow: TvShow)
}