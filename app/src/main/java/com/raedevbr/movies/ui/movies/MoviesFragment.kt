package com.raedevbr.movies.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raedevbr.movies.databinding.FragmentMoviesBinding

class MoviesFragment : Fragment() {

    private val binding by lazy { FragmentMoviesBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root
}