package com.raedevbr.movies.ui.components.movies

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.raedevbr.movies.data.DataRepositorySource
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.ui.base.BaseViewModel
import com.raedevbr.movies.utils.SingleEvent
import com.raedevbr.movies.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val moviesLiveDataPrivate = MutableLiveData<Resource<Movies>>()
    val moviesLiveData: LiveData<Resource<Movies>> get() = moviesLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openMovieDetailsPrivate = MutableLiveData<SingleEvent<MoviesItem>>()
    val openMovieDetails: LiveData<SingleEvent<MoviesItem>> get() = openMovieDetailsPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun getMovies() {
        viewModelScope.launch {
            moviesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepository.requestPopularMovies().collect {
                    moviesLiveDataPrivate.value = it
                }
            }
        }
    }

    fun openMovieDetails(movie: MoviesItem) {
        openMovieDetailsPrivate.value = SingleEvent(movie)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }


}