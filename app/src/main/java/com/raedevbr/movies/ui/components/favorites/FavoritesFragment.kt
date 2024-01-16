package com.raedevbr.movies.ui.components.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.raedevbr.movies.R
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.databinding.FragmentFavoritesBinding
import com.raedevbr.movies.ui.base.BaseFragment
import com.raedevbr.movies.ui.components.movies.adapter.MoviesAdapter
import com.raedevbr.movies.utils.SingleEvent
import com.raedevbr.movies.utils.observe
import com.raedevbr.movies.utils.showToast
import com.raedevbr.movies.utils.toGone
import com.raedevbr.movies.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment() {
    private val binding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<FavoritesViewModel>()
    private val adapter by lazy { MoviesAdapter { movie ->
        viewModel.removeFromFavorite(movie.id)
    } }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFavoritesList.setHasFixedSize(true)
        viewModel.getFavorites()
    }

    private fun bindListData(movies: Movies) {
        if (!(movies.moviesList.isNullOrEmpty())) {
            binding.rvFavoritesList.adapter = adapter
            adapter.submitList(movies.moviesList)
            showDataView(true)
        } else {
            showDataView(false, true)
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(viewLifecycleOwner, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean, isEmpty: Boolean = false) {
        if (isEmpty) binding.tvNoData.text = getString(R.string.no_favorites)
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvFavoritesList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvFavoritesList.toGone()
    }

    private fun handleFavoritesList(status: Resource<List<MoviesItem>>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> {
                val movies = Movies(status.data?.toMutableList() ?: mutableListOf())
                bindListData(movies)
            }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(viewModel.favoritesLiveData, ::handleFavoritesList)
        observeToast(viewModel.showToast)
    }
}