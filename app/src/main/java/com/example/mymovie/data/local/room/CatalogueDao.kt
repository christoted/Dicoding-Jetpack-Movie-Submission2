package com.example.mymovie.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow

@Dao
interface CatalogueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(listMovie : List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTVShow(listTvShow: List<TvShow>)

    @Query("SELECT * FROM movieentities")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("SELECT * FROM movieentities WHERE imbdid = :imbdid")
    fun getMovieByImbdID(imbdid: String) : LiveData<Movie>

    @Query("SELECT * FROM movieentities WHERE bookmarked = 1")
    fun getBookmarkedMovie(): LiveData<List<Movie>>

    @Query("SELECT * FROM tvshowentities")
    fun getAllTVShow(): LiveData<List<TvShow>>

    @Query("SELECT * FROM tvshowentities WHERE imbdid = :imbdid")
    fun getTVShowByImbdID(imbdid: String): LiveData<TvShow>

    @Query("SELECT * FROM tvshowentities WHERE bookmarked = 1")
    fun getBookmarkedTVshow(): LiveData<List<TvShow>>

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