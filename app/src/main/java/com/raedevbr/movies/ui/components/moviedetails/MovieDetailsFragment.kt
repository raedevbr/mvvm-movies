package com.raedevbr.movies.ui.components.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import com.raedevbr.movies.BASE_URL_FILE
import com.raedevbr.movies.R
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.databinding.FragmentMovieDetailsBinding
import com.raedevbr.movies.ui.base.BaseFragment
import com.raedevbr.movies.ui.components.movies.MoviesFragment
import com.raedevbr.movies.utils.SingleEvent
import com.raedevbr.movies.utils.loadImage
import com.raedevbr.movies.utils.observe
import com.raedevbr.movies.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment() {
    private val binding by lazy { FragmentMovieDetailsBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MovieDetailsViewModel>()
    private lateinit var movie: MoviesItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            movie = args.getParcelable(MoviesFragment.ARGS_KEY)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkIsFavorite(movie.id)
        bindMovieDetailsData()
    }

    private fun bindMovieDetailsData() {
        binding.tvMovieTitle.text = movie.title
        binding.tvMovieReleaseDate.text = movie.releaseDate
        binding.tvMovieDescription.text = movie.description
        binding.ivMovieItemImage.loadImage(BASE_URL_FILE + movie.posterPath)
        binding.btnFavorite.setOnClickListener {
            viewModel.addToFavorite(movie)
        }
    }

    private fun bindFavoriteData(movie: MoviesItem?) {
        if (movie != null) {
            showBtnIsFavorite(true)
        } else {
            showBtnIsFavorite(false)
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(viewLifecycleOwner, event, Snackbar.LENGTH_LONG)
    }

    private fun showBtnIsFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.btnFavorite.let { btn ->
                btn.text = getString(R.string.btn_is_favorite)
                btn.setIconResource(R.drawable.ic_favorite)
            }
        } else {
            binding.btnFavorite.let { btn ->
                btn.text = getString(R.string.btn_is_not_favorite)
                btn.setIconResource(R.drawable.ic_favorite_border)
            }
        }
    }

    private fun handleIsFavorite(movie: Resource<MoviesItem?>) {
        when (movie) {
            is Resource.Success -> bindFavoriteData(movie.data)
            else -> {}
        }
    }

    override fun observeViewModel() {
        observe(viewModel.isFavoriteLiveData, ::handleIsFavorite)
        observeToast(viewModel.showToast)
    }
}