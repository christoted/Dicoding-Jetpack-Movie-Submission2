package com.example.mymovie.ui.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.ui.detail.DetailActivity
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.ui.detail.DetailCollapseActivity
import com.example.mymovie.viewmodel.ViewModelFactory
import com.example.mymovie.vo.Resource
import com.example.mymovie.vo.Status


class MovieFragment : Fragment(), MovieItemListener {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel
    private var listMovie: ArrayList<Movie> = ArrayList()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())

            binding.progressBar.visibility = View.VISIBLE

            viewModel = ViewModelProvider(this, viewModelFactory)[MovieViewModel::class.java]

            movieAdapter = MovieAdapter(listMovie, this)

            viewModel.getMovie().observe(viewLifecycleOwner, Observer { movies ->

                when (movies.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewMovie.visibility = View.VISIBLE
                        movies.data?.let {
                            listMovie.addAll(it)
                        }
                        movieAdapter.notifyDataSetChanged()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Error Occurred", Toast.LENGTH_SHORT).show()
                    }
                }

            })

//            listMovie = viewModel.getMovie() as ArrayList<Movie>

            with(binding.recyclerViewMovie) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onMovieItemClicked(Position: Int) {
        // val intent = Intent(context, DetailActivity::class.java)
        val intent = Intent(context, DetailCollapseActivity::class.java)
        val movie = listMovie[Position]
        intent.putExtra(DetailActivity.RECEIVE_INTENT_MOVIE, movie)
        startActivity(intent)
    }
}