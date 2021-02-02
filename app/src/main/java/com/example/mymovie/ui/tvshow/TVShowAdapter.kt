package com.example.mymovie.ui.tvshow

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.databinding.ItemTvShowBinding
import com.example.mymovie.data.local.entity.TvShow

class TVShowAdapter(
    val listTVShow : ArrayList<TvShow>,
    val listener : TVShowListener
) : RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        val itemTvShowBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val tvShow = listTVShow[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int {
        return listTVShow.size
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