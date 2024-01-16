package com.raedevbr.movies.ui.components.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.databinding.FragmentMoviesBinding
import com.raedevbr.movies.ui.base.BaseFragment
import com.raedevbr.movies.ui.components.movies.adapter.MoviesAdapter
import com.raedevbr.movies.utils.SingleEvent
import com.raedevbr.movies.utils.observe
import com.raedevbr.movies.utils.showToast
import com.raedevbr.movies.utils.toGone
import com.raedevbr.movies.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment() {

    private val binding by lazy { FragmentMoviesBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MoviesViewModel>()
    private val adapter by lazy { MoviesAdapter { movie ->
        // TODO("replace this code later for navigate to MovieDetailsFragment")
        viewModel.addToFavorite(movie)
    } }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvMoviesList.setHasFixedSize(true)
        viewModel.getMovies()
    }

    private fun bindListData(movies: Movies) {
        if (!(movies.moviesList.isNullOrEmpty())) {
            binding.rvMoviesList.adapter = adapter
            adapter.submitList(movies.moviesList)
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(viewLifecycleOwner, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) View.GONE else View.VISIBLE
        binding.rvMoviesList.visibility = if (show) View.VISIBLE else View.GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvMoviesList.toGone()
    }

    private fun handleMoviesList(status: Resource<Movies>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(movies = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { viewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(viewModel.moviesLiveData, ::handleMoviesList)
        observeToast(viewModel.showToast)
    }


}