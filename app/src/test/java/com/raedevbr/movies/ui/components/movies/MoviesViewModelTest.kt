package com.raedevbr.movies.ui.components.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raedevbr.movies.InstantExecutorExtension
import com.raedevbr.movies.MainCoroutineRule
import com.raedevbr.movies.TestModelsGenerator
import com.raedevbr.movies.data.DataRepository
import com.raedevbr.movies.data.Resource
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.error.NETWORK_ERROR
import io.mockk.coEvery
import org.junit.Assert.assertEquals
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class MoviesViewModelTest {
    private lateinit var viewModel: MoviesViewModel
    private val dataRepository: DataRepository = mockk()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Test
    fun `get Movies List`() {
        // create an answer for the liveData
        val moviesModel = testModelsGenerator.generateMovies()

        //1- mock calls
        coEvery { dataRepository.requestPopularMovies() } returns flow {
            emit(Resource.Success(moviesModel))
        }

        //2- call
        viewModel = MoviesViewModel(dataRepository)
        viewModel.getMovies()
        // active observer for liveData
        viewModel.moviesLiveData.observeForever {  }

        //3- verify
        val isEmptyList = viewModel.moviesLiveData.value?.data?.moviesList.isNullOrEmpty()
        assertEquals(moviesModel, viewModel.moviesLiveData.value?.data)
        assertEquals(false, isEmptyList)
    }

    @Test
    fun `get Movies Empty List`() {
        // create an answer for the liveData
        val moviesModel = testModelsGenerator.generateMoviesModelWithEmptyList()

        //1- mock calls
        coEvery { dataRepository.requestPopularMovies() } returns flow {
            emit(Resource.Success(moviesModel))
        }

        //2- call
        viewModel = MoviesViewModel(dataRepository)
        viewModel.getMovies()
        // active observer for liveData
        viewModel.moviesLiveData.observeForever {  }

        //3- verify
        val isEmptyList = viewModel.moviesLiveData.value?.data?.moviesList.isNullOrEmpty()
        assertEquals(moviesModel, viewModel.moviesLiveData.value?.data)
        assertEquals(true, isEmptyList)
    }

    @Test
    fun `get Movies Error`() {
        // create an answer for the liveData
        val error: Resource<Movies> = Resource.DataError(NETWORK_ERROR)

        //1- mock calls
        coEvery { dataRepository.requestPopularMovies() } returns flow {
            emit(error)
        }

        //2- call
        viewModel = MoviesViewModel(dataRepository)
        viewModel.getMovies()
        // active observer for liveData
        viewModel.moviesLiveData.observeForever {  }

        //3- verify
        assertEquals(NETWORK_ERROR, viewModel.moviesLiveData.value?.errorCode)
    }

}