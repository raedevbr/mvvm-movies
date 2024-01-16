package com.raedevbr.movies.ui.components.favorites

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
class FavoritesViewModel @Inject constructor(
    private val dataRepository: DataRepositorySource
) : BaseViewModel() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val favoritesLiveDataPrivate = MutableLiveData<Resource<List<MoviesItem>>>()
    val favoritesLiveData: LiveData<Resource<List<MoviesItem>>> get() = favoritesLiveDataPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate

    fun getFavorites() {
        viewModelScope.launch {
            favoritesLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepository.getCachedFavorites().collect {
                    favoritesLiveDataPrivate.value = it
                }
            }
        }
    }

    fun removeFromFavorite(id: Long) {
        viewModelScope.launch {
            wrapEspressoIdlingResource {
                dataRepository.removeFromFavorite(id).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            val isSuccess = result.data ?: false
                            if (isSuccess) {
                                showToastPrivate.value = SingleEvent("removed from favorites")
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