package com.example.mymovie.di

import android.content.Context
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.remote.RemoteDataSource
import com.example.mymovie.utils.JsonHelper

object Injection {
    fun provideRepository(context : Context): FilmRepository {
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return FilmRepository.getInstance(remoteDataSource)
    }
}