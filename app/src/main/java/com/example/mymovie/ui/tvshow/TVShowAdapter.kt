package com.example.mymovie.ui.tvshow

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.databinding.ItemTvShowBinding
import com.example.mymovie.data.local.entity.TvShow

class TVShowAdapter(
    val listener : TVShowListener
) : PagedListAdapter<TvShow,TVShowAdapter.TVShowViewHolder>(DIFF_UTIL) {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val itemTvShowBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if ( tvShow != null) {
            holder.bind(tvShow)
        }

    }


    inner class TVShowViewHolder ( private val binding : ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onTVShowClickedListener(adapterPosition)
            }
        }

        fun bind(tvShow: TvShow) {
            with(binding) {
                textViewTitleTvShow.text = tvShow.Title
                textViewTitleReleaseDateTvShow.text = tvShow.Year
                Glide.with(itemView.context)
                        .load(tvShow.Poster)
                        .placeholder(R.drawable.ic_loading)
                        .into(binding.imageViewTvShow)
            }
        }
    }


}