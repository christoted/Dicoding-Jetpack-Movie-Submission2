package com.example.mymovie.ui.movie

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovie.R
import com.example.mymovie.databinding.FragmentMovieBinding
import com.example.mymovie.databinding.ItemMovieBinding
import com.example.mymovie.data.local.entity.Movie

class MovieAdapter(
    var listMovie : ArrayList<Movie>,
    val listener : MovieItemListener
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovie[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return listMovie.size
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