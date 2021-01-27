package com.example.mymovie.data.remote

import android.os.Handler
import com.example.mymovie.data.remote.response.MovieResponse
import com.example.mymovie.data.remote.response.TVShowResponse
import com.example.mymovie.utils.EspressoIdlingResource
import com.example.mymovie.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper){

    private val handler = Handler()

    companion object {

        private const val SERVICE_LATENCY_IN_MILIS: Long = 1000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper) : RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper)
            }
    }

    fun getAllMovie(callback : LoadMovieCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({callback.onAllMovieReceived(jsonHelper.loadMovie())
        EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILIS)
    }

  //  fun getAllMovie() : List<MovieResponse> = jsonHelper.loadMovie()

  //  fun getAllTVShow() : List<TVShowResponse> = jsonHelper.loadTVShow()

    fun getAllTVShow(callback : LoadTVShowCallback) {
        EspressoIdlingResource.increment()
        handler.postDelayed({callback.onAllTVShowReceived(jsonHelper.loadTVShow())
                            EspressoIdlingResource.decrement()}, SERVICE_LATENCY_IN_MILIS)
    }


    interface LoadMovieCallback {
        fun onAllMovieReceived( listMovieResponse: List<MovieResponse>)
    }

    interface LoadTVShowCallback {
        fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>)
    }

}