package com.example.mymovie.ui.favourite.moviefav

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.R
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.databinding.FragmentFavouriteBinding
import com.example.mymovie.databinding.FragmentMovieFavBinding
import com.example.mymovie.ui.movie.MovieAdapter
import com.example.mymovie.ui.movie.MovieFragment
import com.example.mymovie.ui.movie.MovieItemListener
import com.example.mymovie.viewmodel.ViewModelFactory

class MovieFavFragment : Fragment(), MovieItemListener {

    private lateinit var viewModel: MovieFavViewModel

    private lateinit var listSavedMovie: ArrayList<Movie>

    private lateinit var binding: FragmentMovieFavBinding

    companion object {
        val TAG = MovieFavFragment::class.java.simpleName
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieFavBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listSavedMovie = ArrayList()

        val movieAdapter = MovieAdapter(this@MovieFavFragment)

        if ( activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, viewModelFactory)[MovieFavViewModel::class.java]
            Log.d(TAG, "onViewCreated: ${listSavedMovie.size}")
            viewModel.getMovieSaved().observe(viewLifecycleOwner, Observer {
              //  listSavedMovie.addAll(it)
                Log.d(TAG, "onViewCreated: ${listSavedMovie.size}")
                movieAdapter.submitList(it)
            //    movieAdapter.notifyDataSetChanged()
            })
        }

        binding.recyclerViewFav.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = movieAdapter
        }
    }

    override fun onMovieItemClicked(Position: Int) {

    }

}