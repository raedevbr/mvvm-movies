package com.raedevbr.movies.ui.components.movies.adapter

import androidx.recyclerview.widget.DiffUtil
import com.raedevbr.movies.data.dto.movies.MoviesItem

class MovieDiffCallback : DiffUtil.ItemCallback<MoviesItem>() {
    override fun areItemsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MoviesItem, newItem: MoviesItem): Boolean {
        return oldItem == newItem
    }
}