package com.example.mymovie.di

import android.content.Context
import com.example.mymovie.data.FilmRepository
import com.example.mymovie.data.local.LocalDataSource
import com.example.mymovie.data.local.room.CatalogueDao
import com.example.mymovie.data.local.room.CatalogueDatabase
import com.example.mymovie.data.remote.RemoteDataSource
import com.example.mymovie.utils.JsonHelper

object Injection {
    fun provideRepository(context : Context): FilmRepository {

        val database = CatalogueDatabase.getInstance(context)

        val localDataSource = LocalDataSource.getInstance(database.catalogueDao())
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))
        return FilmRepository.getInstance(remoteDataSource, localDataSource)
    }
}