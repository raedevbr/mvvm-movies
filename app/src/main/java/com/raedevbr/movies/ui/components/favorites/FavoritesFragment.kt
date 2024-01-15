package com.raedevbr.movies.ui.components.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.raedevbr.movies.databinding.FragmentFavoritesBinding
import com.raedevbr.movies.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment() {

    private val binding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FavoritesViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun observeViewModel() {
        // TODO
    }
}