package com.example.mymovie.data.remote.api

import androidx.lifecycle.MutableLiveData
import com.example.mymovie.data.remote.ApiResponse
import com.example.mymovie.data.remote.response.*
import retrofit2.Call
import retrofit2.http.GET

interface CatalogueApi {
    @GET("?apikey=99182b8c&s=*hero*&page=1")
    suspend fun getMovie(): MovieServiceResponse

    @GET("?apikey=99182b8c&s=*panda*&page=1")
    suspend fun getTVShow():  TVShowServiceResponse
}