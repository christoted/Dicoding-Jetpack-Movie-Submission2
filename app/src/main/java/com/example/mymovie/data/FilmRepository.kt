package com.example.mymovie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovie.data.local.LocalDataSource
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.data.remote.ApiResponse
import com.example.mymovie.data.remote.RemoteDataSource
import com.example.mymovie.data.remote.response.MovieResponse
import com.example.mymovie.data.remote.response.TVShowResponse
import com.example.mymovie.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FilmRepository private constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource) :

    FilmDataSource {

    companion object {
        @Volatile
        private var instance: FilmRepository? = null

        fun getInstance(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) : FilmRepository =
            instance ?: synchronized(this) {
                instance ?: FilmRepository(remoteDataSource, localDataSource)
            }
    }

    override fun getAllMovie(): LiveData<Resource<List<Movie>>> {
        return object : NetworkBoundResource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): LiveData<List<Movie>> {
                return localDataSource.getAllMovies()
            }

            override fun shouldFetch(data: List<Movie>): Boolean {
                return data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
               return  remoteDataSource.getAllMovie()
            }

            override fun saveCallForResult(body: List<MovieResponse>) {
                val movieList = ArrayList<Movie>()
                for ( movie in body) {
                    val movie = Movie(movie.Poster, movie.Title, movie.Type, movie.Year, movie.imdbID, false)
                    movieList.add(movie)
                }
                localDataSource.insertMovie(movieList)
            }

        }.asLiveData()
    }

    override fun getAllTVShow(): LiveData<Resource<List<TvShow>>> {
        return object : NetworkBoundResource<List<TvShow>, List<TVShowResponse>>() {
            override fun loadFromDB(): LiveData<List<TvShow>> {
               return localDataSource.getAllTVShow()
            }

            override fun shouldFetch(data: List<TvShow>): Boolean {
                return data.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<TVShowResponse>>> {
                return remoteDataSource.getAllTVShow()
            }

            override fun saveCallForResult(body: List<TVShowResponse>) {
                val listTVShow = ArrayList<TvShow>()

                for ( tvShow in body) {
                    val tvShow = TvShow(tvShow.Poster, tvShow.Title, tvShow.Type, tvShow.Year, tvShow.imdbID, false)
                    listTVShow.add(tvShow)
                }

                localDataSource.insertTVShow(listTVShow)
            }

        }.asLiveData()
    }



//    override fun getAllMovie(): LiveData<List<Movie>> {
//
//        val movieResponse = MutableLiveData<List<Movie>>()
//        remoteDataSource.getAllMovie(object : RemoteDataSource.LoadMovieCallback {
//            override fun onAllMovieReceived(listMovieResponse: List<MovieResponse>) {
//                val listMovie = ArrayList<Movie>()
//
//                for ( response in listMovieResponse) {
//                    val movie = Movie(response.Poster, response.Title, response.Type, response.Year, response.imdbID)
//
//                    listMovie.add(movie)
//                }
//                movieResponse.postValue(listMovie)
//            }
//
//        })
//        return movieResponse
//    }

//    override fun getAllTVShow(): LiveData<List<TvShow>> {
//
//        val tvShowResponse = MutableLiveData<List<TvShow>>()
//        remoteDataSource.getAllTVShow(object : RemoteDataSource.LoadTVShowCallback {
//            override fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>) {
//                val listTVShow = ArrayList<TvShow>()
//
//                for ( response in listTVShowResponse) {
//                    val tvShow = TvShow(response.Poster, response.Title, response.Type, response.Year, response.imdbID)
//
//                    listTVShow.add(tvShow)
//                }
//
//                tvShowResponse.postValue(listTVShow)
//            }
//
//        })
//
//
//
//        return tvShowResponse
//    }
//
//    override fun getSelectedMovie(imdbID: String): LiveData<Movie> {
//        val movieResponse = MutableLiveData<Movie>()
//        remoteDataSource.getAllMovie(object : RemoteDataSource.LoadMovieCallback {
//            override fun onAllMovieReceived(listMovieResponse: List<MovieResponse>) {
//                lateinit var movie: Movie
//
//                for ( response in listMovieResponse) {
//                    if ( response.imdbID == imdbID) {
//                        movie = Movie(response.Poster, response.Title, response.Type, response.Year, response.imdbID)
//                    }
//                }
//
//                movieResponse.postValue(movie)
//            }
//
//        })
//
//
//        return movieResponse
//    }
//
//    override fun getSelectedTVShow(imdbID: String): LiveData<TvShow> {
//
//        val tvShowResponse = MutableLiveData<TvShow>()
//
//        remoteDataSource.getAllTVShow(object : RemoteDataSource.LoadTVShowCallback {
//            override fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>) {
//                lateinit var tvShow: TvShow
//
//                for ( response in listTVShowResponse) {
//                    if (response.imdbID == imdbID) {
//                        tvShow = TvShow(response.Poster,response.Title, response.Type, response.Year, response.imdbID)
//                    }
//                }
//
//                tvShowResponse.postValue(tvShow)
//            }
//
//        })
//
//        return tvShowResponse
//    }

    override fun setBookmarkedMovie(movie: Movie, newState: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.setBookmarkedMovie(movie, newState)
        }
    }

    override fun setBookmarkedTVShow(tvShow: TvShow, newState: Boolean) {
        GlobalScope.launch(Dispatchers.IO) {
            localDataSource.setBookmarkedTVShow(tvShow, newState)
        }
    }


}