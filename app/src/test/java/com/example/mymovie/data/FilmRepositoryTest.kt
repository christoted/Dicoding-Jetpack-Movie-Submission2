package com.example.mymovie.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mymovie.data.remote.RemoteDataSource
import com.example.mymovie.data.remote.response.MovieResponse
import com.example.mymovie.util.LiveDataUtil
import com.example.mymovie.utils.FakeData
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any

class FilmRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val filmRepository = FakeFilmRepository(remote)

    private val tvShowResponse = FakeData.generateRemoteDummyTVShow()
    private val movieResponse = FakeData.generatesRemoteDummyMovie()

    private val tvShowId = tvShowResponse[0].imdbID
    private val movieId = movieResponse[0].imdbID

    @Test
    fun getAllMovie() {

        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback).onAllMovieReceived(
                movieResponse
            )
            null
        }.`when`(remote).getAllMovie(com.nhaarman.mockitokotlin2.any())

        val movieEntity = LiveDataUtil.getValue(filmRepository.getAllMovie())
        verify(remote).getAllMovie(com.nhaarman.mockitokotlin2.any())
        assertNotNull(movieEntity)
        assertEquals(movieResponse.size.toLong(), movieEntity.size.toLong())
    }

    @Test
    fun getAllTVShow() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTVShowCallback).onAllTVShowReceived(
                tvShowResponse
            )
            null
        }.`when`(remote).getAllTVShow(com.nhaarman.mockitokotlin2.any())

        val tvShowEntity = LiveDataUtil.getValue(filmRepository.getAllTVShow())
        verify(remote).getAllTVShow(com.nhaarman.mockitokotlin2.any())
        assertNotNull(tvShowEntity)
        assertEquals(tvShowResponse.size.toLong(), tvShowEntity.size.toLong())
    }

    @Test
    fun getSelectedMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadMovieCallback).onAllMovieReceived(
                movieResponse
            )
            null
        }.`when`(remote).getAllMovie(com.nhaarman.mockitokotlin2.any())

        val selectedMovie = LiveDataUtil.getValue(filmRepository.getSelectedMovie(movieId))
        verify(remote).getAllMovie(com.nhaarman.mockitokotlin2.any())

        assertNotNull(selectedMovie)
        assertEquals(movieResponse[0].Title, selectedMovie.Title)
    }

    @Test
    fun getSelectedTVShow() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteDataSource.LoadTVShowCallback).onAllTVShowReceived(
                tvShowResponse
            )
            null
        }.`when`(remote).getAllTVShow(com.nhaarman.mockitokotlin2.any())

        val selectedTVShow = LiveDataUtil.getValue(filmRepository.getSelectedTVShow(tvShowId))
        verify(remote).getAllTVShow(com.nhaarman.mockitokotlin2.any())

        assertNotNull(selectedTVShow)
        assertEquals(tvShowResponse[0].Title, selectedTVShow.Title)

    }
}