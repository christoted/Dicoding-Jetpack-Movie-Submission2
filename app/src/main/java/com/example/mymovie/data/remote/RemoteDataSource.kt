package com.example.mymovie.data.remote

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovie.data.remote.response.MovieResponse
import com.example.mymovie.data.remote.response.TVShowResponse
import com.example.mymovie.utils.EspressoIdlingResource
import com.example.mymovie.utils.JsonHelper
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

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

//    fun getAllMovie(callback : LoadMovieCallback) {
//        EspressoIdlingResource.increment()
//        handler.postDelayed({callback.onAllMovieReceived(jsonHelper.loadMovie())
//        EspressoIdlingResource.decrement()
//        }, SERVICE_LATENCY_IN_MILIS)
//    }

      fun getAllMovie() : LiveData<ApiResponse<List<MovieResponse>>> {
          EspressoIdlingResource.increment()
          val resultMovie = MutableLiveData<ApiResponse<List<MovieResponse>>>()
          GlobalScope.launch (Dispatchers.IO){
              delay(1000)
              EspressoIdlingResource.decrement()
              resultMovie.postValue(ApiResponse.success(jsonHelper.loadMovie()))
//              withContext(Dispatchers.Main) {
//
//              }
          }

          return resultMovie
      }

  //  fun getAllMovie() : List<MovieResponse> = jsonHelper.loadMovie()

  //  fun getAllTVShow() : List<TVShowResponse> = jsonHelper.loadTVShow()

    fun getAllTVShow(): LiveData<ApiResponse<List<TVShowResponse>>> {
        EspressoIdlingResource.increment()
        val resultTVShow = MutableLiveData<ApiResponse<List<TVShowResponse>>>()
        GlobalScope.launch(Dispatchers.IO) {
            delay(1000)
            EspressoIdlingResource.decrement()
            resultTVShow.postValue(ApiResponse.success(jsonHelper.loadTVShow()))
        }

        return resultTVShow
    }

//    fun getAllTVShow(callback : LoadTVShowCallback) {
//        EspressoIdlingResource.increment()
//        handler.postDelayed({callback.onAllTVShowReceived(jsonHelper.loadTVShow())
//                            EspressoIdlingResource.decrement()}, SERVICE_LATENCY_IN_MILIS)
//    }

//    fun getSelectedTVShow(imbdId: String): LiveData<ApiResponse<TVShowResponse>> {
//        EspressoIdlingResource.increment()
//        val resultSelectedTVShow = MutableLiveData<ApiResponse<TVShowResponse>>()
//        GlobalScope.launch(Dispatchers.IO) {
//            delay(1000)
//            EspressoIdlingResource.decrement()
//            resultSelectedTVShow.postValue(ApiResponse.success(jsonHelper.))
//        }
//    }


    interface LoadMovieCallback {
        fun onAllMovieReceived( listMovieResponse: List<MovieResponse>)
    }

    interface LoadTVShowCallback {
        fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>)
    }

}