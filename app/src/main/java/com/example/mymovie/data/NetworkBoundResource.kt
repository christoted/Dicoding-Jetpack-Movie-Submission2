package com.example.mymovie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.mymovie.data.remote.ApiResponse
import com.example.mymovie.data.remote.StatusResponse
import com.example.mymovie.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class NetworkBoundResource<ResultType, RequestType>() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    abstract fun loadFromDB(): LiveData<ResultType>

    abstract fun shouldFetch(data: ResultType): Boolean

    abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    abstract fun saveCallForResult(body: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) { newData ->
            result.value = Resource.loading(newData)
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCESS -> {
                    GlobalScope.launch(Dispatchers.IO) {
                        saveCallForResult(response.body)
                        withContext(Dispatchers.Main) {
                            result.addSource(loadFromDB()) { newData ->
                                result.value = Resource.success(newData)
                            }
                        }
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}