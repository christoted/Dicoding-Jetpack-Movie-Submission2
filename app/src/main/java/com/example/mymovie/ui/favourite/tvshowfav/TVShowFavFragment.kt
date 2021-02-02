package com.example.mymovie.ui.favourite.tvshowfav

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovie.R
import com.example.mymovie.data.local.entity.TvShow
import com.example.mymovie.databinding.FragmentTVShowFavBinding
import com.example.mymovie.ui.tvshow.TVShowAdapter
import com.example.mymovie.ui.tvshow.TVShowListener
import com.example.mymovie.viewmodel.ViewModelFactory


class TVShowFavFragment : Fragment(), TVShowListener {

    private lateinit var binding: FragmentTVShowFavBinding

    private lateinit var viewModel: TVShowFavViewModel

    private lateinit var tvShowAdapter: TVShowAdapter

    private lateinit var listSavedTVShow: ArrayList<TvShow>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTVShowFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listSavedTVShow = ArrayList()

        tvShowAdapter = TVShowAdapter(this@TVShowFavFragment)

        if ( activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TVShowFavViewModel::class.java]

            viewModel.getSavedTVShow().observe(viewLifecycleOwner, Observer {
              //  listSavedTVShow.addAll(it)
                tvShowAdapter.submitList(it)
            })
        }

        binding.recylerViewFavTVShow.apply {
            adapter = tvShowAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
    }

    override fun onTVShowClickedListener(Position: Int) {

    }

}