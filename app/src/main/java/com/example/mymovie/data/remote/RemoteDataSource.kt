package com.example.mymovie.data.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovie.data.remote.api.ApiConfig
import com.example.mymovie.data.remote.response.*
import com.example.mymovie.utils.EspressoIdlingResource
import kotlinx.coroutines.*

class RemoteDataSource {

    fun getAllMovie() : LiveData<ApiResponse<MovieServiceResponse>> = runBlocking {
        val resultMovie = MutableLiveData<ApiResponse<MovieServiceResponse>>()
        val movies = async { getMovies() }
        val result = movies.await()
        resultMovie.postValue(ApiResponse.success(result))

        return@runBlocking resultMovie
    }

     private suspend fun getMovies() : MovieServiceResponse{
         return ApiConfig.getApiService().getMovie()
    }

    fun getAllTVShow(): LiveData<ApiResponse<TVShowServiceResponse>> {
        EspressoIdlingResource.increment()
        val resultTVShow = MutableLiveData<ApiResponse<TVShowServiceResponse>>()

        GlobalScope.launch(Dispatchers.IO) {
            val tvShowService = ApiConfig.getApiService().getTVShow()
            EspressoIdlingResource.decrement()
            resultTVShow.postValue(ApiResponse.success(tvShowService))
        }

        return resultTVShow
    }


    interface LoadMovieCallback {
        fun onAllMovieReceived( listMovieResponse: List<MovieResponse>)
    }

    interface LoadTVShowCallback {
        fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>)
    }

}