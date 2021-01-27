package com.example.mymovie.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.entity.Movie
import com.example.mymovie.data.entity.TvShow
import com.example.mymovie.ui.movie.MovieViewModel
import com.example.mymovie.ui.tvshow.TvShowViewModel
import com.example.mymovie.utils.FakeData
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {
    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var filmRepository: FilmRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<List<TvShow>>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(filmRepository)
    }
    @Test
    fun getTVShow() {

        val tvShowDummy = FakeData.generateFakeTVShows()
        val tvShow = MutableLiveData<List<TvShow>>()
        tvShow.value = tvShowDummy

        `when`(filmRepository.getAllTVShow()).thenReturn(tvShow)
        val tvShows = viewModel.getTVShow().value
        assertNotNull(tvShows)
        assertEquals(10, tvShows?.size)

        viewModel.getTVShow().observeForever(observer)
        verify(observer).onChanged(tvShowDummy)
    }

    /*
        Scenario pengujian ( menggunakan InstantTaskExecutorRule untuk proses async )
        1. Memastikan bahwa method di dalam filmRepository dapat mengenerate data
        2. Mengecek bahwa hasil dari viewModel.getTVShow().value tidak sama dengan null
        3. Mengecek jumlah data viewModel.getTVShow().value sama dengan 10
    */
}