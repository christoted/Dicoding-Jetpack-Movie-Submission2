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

    override fun getSelectedMovie(imdbID: String): LiveData<Resource<Movie>> {
        return object : NetworkBoundResource<Movie, List<MovieResponse>>() {
            override fun loadFromDB(): LiveData<Movie> {
                return localDataSource.getMovieByImbdID(imdbID)
            }

            override fun shouldFetch(data: Movie): Boolean {
                return data.imdbID.isEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovie()
            }

            override fun saveCallForResult(body: List<MovieResponse>) {
                val listMovie = ArrayList<Movie>()

                for ( movie in body) {
                    val movie = Movie(movie.Poster, movie.Title, movie.Type, movie.Year, movie.imdbID, false)
                    listMovie.add(movie)
                }

                localDataSource.insertMovie(listMovie)
            }

        }.asLiveData()
    }

    override fun getSelectedTVShow(imdbID: String): LiveData<Resource<TvShow>> {
        return object : NetworkBoundResource<TvShow, List<TVShowResponse>>() {
            override fun loadFromDB(): LiveData<TvShow> {
                return localDataSource.getTVShowByImbdID(imdbID)
            }

            override fun shouldFetch(data: TvShow): Boolean {
                return data.imdbID.isEmpty()
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

    override fun getBookmarkedTVShow(): LiveData<List<TvShow>> {
        return localDataSource.getBookmarkedTVShow()
    }

    override fun getBookmarkedMovie(): LiveData<List<Movie>> {
        return localDataSource.getBookmarkedMovie()
    }


}