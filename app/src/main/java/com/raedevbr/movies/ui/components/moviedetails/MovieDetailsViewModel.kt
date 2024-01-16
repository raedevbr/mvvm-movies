package com.raedevbr.movies.ui.components.moviedetails

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raedevbr.movies.data.DataRepositorySource
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.ui.base.BaseViewModel
import com.raedevbr.movies.utils.SingleEvent
import com.raedevbr.movies.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val isFavoriteLiveDataPrivate = MutableLiveData<Resource<MoviesItem?>>()
    val isFavoriteLiveData: LiveData<Resource<MoviesItem?>> get() = isFavoriteLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun checkIsFavorite(movieId: Long) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.isFavorite(movieId).collect {
                    isFavoriteLiveDataPrivate.value = it
                }
            }
        }
    }

    // TODO("replace this code with room upsert later")
    fun addToFavorite(movie: MoviesItem) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.addToFavorite(movie).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val isSuccess = result.data ?: false
                            if (isSuccess) {
                                showToastPrivate.value = SingleEvent("added to favorites")
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}