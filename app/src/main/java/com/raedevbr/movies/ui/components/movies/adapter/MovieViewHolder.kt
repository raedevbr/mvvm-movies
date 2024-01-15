package com.raedevbr.movies.ui.components.movies.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.raedevbr.movies.BASE_URL_FILE
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.databinding.MovieItemBinding
import com.raedevbr.movies.utils.loadImage


class MovieViewHolder(private val itemBinding: MovieItemBinding) : RecyclerView.ViewHolder(itemBinding.root){

    fun bind(moviesItem: MoviesItem) {
        itemBinding.ivMovieItemImage.loadImage(BASE_URL_FILE + moviesItem.posterPath)
    }
}