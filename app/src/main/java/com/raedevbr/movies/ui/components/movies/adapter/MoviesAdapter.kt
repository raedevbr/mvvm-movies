package com.raedevbr.movies.ui.components.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.databinding.MovieItemBinding

class MoviesAdapter(
    private val onClick: (MoviesItem) -> Unit
) : ListAdapter<MoviesItem, MovieViewHolder>(MovieDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { onClick(item) }
        holder.bind(item)
    }
}