package com.example.mymovie.ui.movie

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.databinding.ItemMovieBinding
import com.example.mymovie.data.local.entity.Movie
import com.example.mymovie.ui.detail.DetailActivity
import com.example.mymovie.ui.detail.DetailCollapseActivity

class MovieAdapter(
    val listener : MovieItemListener
) : PagedListAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.imdbID == newItem.imdbID
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if ( movie != null) {
            holder.bind(movie)
        }

    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                listener.onMovieItemClicked(adapterPosition)
            }
        }

        fun bind(movie: Movie) {
            with(binding) {
                textViewTitleMovie.text = movie.Title
                textViewTitleReleaseDate.text = movie.Year
                Glide.with(itemView.context)
                        .load(movie.Poster)
                        .placeholder(R.drawable.ic_loading)
                        .into(imageViewMovie)


            }
        }
    }
}