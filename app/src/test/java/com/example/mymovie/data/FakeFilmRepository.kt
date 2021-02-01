package com.example.mymovie.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.data.remote.RemoteDataSource
import com.example.mymovie.data.remote.response.MovieResponse
import com.example.mymovie.data.remote.response.TVShowResponse

class FakeFilmRepository (private val remoteDataSource: RemoteDataSource) : FilmDataSource {

    override fun getAllMovie(): LiveData<List<Movie>> {

        val movieResponse = MutableLiveData<List<Movie>>()
     //   val movieResponses = remoteDataSource.getAllMovie()
        remoteDataSource.getAllMovie(object : RemoteDataSource.LoadMovieCallback {
            override fun onAllMovieReceived(listMovieResponse: List<MovieResponse>) {
                val listMovie = ArrayList<Movie>()

                for ( response in listMovieResponse) {
                    val movie = Movie(response.Poster, response.Title, response.Type, response.Year, response.imdbID)

                    listMovie.add(movie)
                }
                movieResponse.postValue(listMovie)
            }

        })

//        return listMovie
        return movieResponse
    }

    override fun getAllTVShow(): LiveData<List<TvShow>> {

        val tvShowResponse = MutableLiveData<List<TvShow>>()
        remoteDataSource.getAllTVShow(object : RemoteDataSource.LoadTVShowCallback {
            override fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>) {
                val listTVShow = ArrayList<TvShow>()

                for ( response in listTVShowResponse) {
                    val tvShow = TvShow(response.Poster, response.Title, response.Type, response.Year, response.imdbID)

                    listTVShow.add(tvShow)
                }

                tvShowResponse.postValue(listTVShow)
            }

        })

   //     val tvShowResponse = remoteDataSource.getAllTVShow()


        return tvShowResponse
    }

    override fun getSelectedMovie(imdbID: String): LiveData<Movie> {
        val movieResponse = MutableLiveData<Movie>()
    //    val movieResponse = remoteDataSource.getAllMovie()
        remoteDataSource.getAllMovie(object : RemoteDataSource.LoadMovieCallback {
            override fun onAllMovieReceived(listMovieResponse: List<MovieResponse>) {
                lateinit var movie: Movie

                for ( response in listMovieResponse) {
                    if ( response.imdbID == imdbID) {
                        movie = Movie(response.Poster, response.Title, response.Type, response.Year, response.imdbID)
                    }
                }

                movieResponse.postValue(movie)
            }

        })


        return movieResponse
    }

    override fun getSelectedTVShow(imdbID: String): LiveData<TvShow> {

        val tvShowResponse = MutableLiveData<TvShow>()

        remoteDataSource.getAllTVShow(object : RemoteDataSource.LoadTVShowCallback {
            override fun onAllTVShowReceived(listTVShowResponse: List<TVShowResponse>) {
                lateinit var tvShow: TvShow

                for ( response in listTVShowResponse) {
                    if (response.imdbID == imdbID) {
                        tvShow = TvShow(response.Poster,response.Title, response.Type, response.Year, response.imdbID)
                    }
                }

                tvShowResponse.postValue(tvShow)
            }

        })

    //    val tvShowResponse = remoteDataSource.getAllTVShow()


        return tvShowResponse
    }


}