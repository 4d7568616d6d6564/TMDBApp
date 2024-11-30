package com.mkdevelopment.tmdbapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkdevelopment.tmdbapp.databinding.ItemHomeRecyclerviewBinding
import com.mkdevelopment.tmdbapp.model.MovieItem
import com.mkdevelopment.tmdbapp.util.Constants.Companion.IMAGE_BASE_URL
import com.mkdevelopment.tmdbapp.util.loadCircleImage

interface MovieClickListener {
    fun onMovieClickListener(movieId: Int?)
}

class MovieAdapter(private val movieClickListener: MovieClickListener) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList: List<MovieItem> = emptyList()

    // private var movieList: MutableList<MovieItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.titleTextView.text = movie?.title
        holder.binding.descriptionTextView.text = movie?.overview

        holder.binding.starTextView.text = movie?.voteAverage.toString()

        holder.binding.posterImageView.loadCircleImage(IMAGE_BASE_URL + movie?.posterPath)

        holder.itemView.setOnClickListener {
            movieClickListener.onMovieClickListener(movie.id)
        }
    }

    inner class ViewHolder(val binding: ItemHomeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setMovieList(list: List<MovieItem?>?) {
        movieList = list?.filterNotNull() ?: emptyList()
        notifyDataSetChanged()
    }
}