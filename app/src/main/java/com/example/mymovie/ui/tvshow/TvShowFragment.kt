package com.example.mymovie.ui.tvshow

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentTvShowBinding
import com.example.mymovie.databinding.ItemTvShowBinding
import com.example.mymovie.ui.detail.DetailActivity
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.ui.detail.DetailCollapseActivity
import com.example.mymovie.viewmodel.ViewModelFactory
import com.example.mymovie.vo.Status

class TvShowFragment : Fragment(), TVShowListener {

    private lateinit var binding: FragmentTvShowBinding
    private lateinit var viewModel: TvShowViewModel
    private lateinit var tvShowAdapter: TVShowAdapter
    private var listTVShow: ArrayList<TvShow> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            binding.progressBar.visibility = View.VISIBLE

            val viewModelFactory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, viewModelFactory)[TvShowViewModel::class.java]
            tvShowAdapter = TVShowAdapter(this)
            viewModel.getTVShow().observe(viewLifecycleOwner, Observer { tvShows ->

                when (tvShows.status) {
                    Status.SUCCESS -> {

                        binding.progressBar.visibility = View.GONE
                        binding.recyclerViewTVShow.visibility = View.VISIBLE
                        tvShows.data?.let {
                            tvShowAdapter.submitList(it)
                        }
                    }

                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireActivity(), "Error Occurred", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


            })

            //   listTVShow = viewModel.getTVShow() as ArrayList<TvShow>

            with(binding.recyclerViewTVShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowAdapter

            }
        }


    }

    override fun onTVShowClickedListener(Position: Int) {
        val tvShow = tvShowAdapter.currentList?.get(Position)
        val intent = Intent(context, DetailCollapseActivity::class.java)
        intent.putExtra(DetailActivity.RECEIVE_INTENT_TVSHOWS, tvShow)
        startActivity(intent)
    }
}