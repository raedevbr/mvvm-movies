package com.raedevbr.movies

import com.google.gson.Gson
import com.raedevbr.movies.data.dto.movies.Movies
import com.raedevbr.movies.data.dto.movies.MoviesItem
import java.io.BufferedReader
import java.io.File
import java.io.InputStream

class TestModelsGenerator {
    private var movies: Movies = Movies(arrayListOf())

    init {
        val jsonString = getJson("MoviesApiResponse.json")
        val gson = Gson()

        movies = gson.fromJson(jsonString, Movies::class.java)
        print("this is $movies")
    }

    fun generateMovies(): Movies {
        return movies
    }

    fun generateMoviesModelWithEmptyList(): Movies {
        return Movies(arrayListOf())
    }

    fun generateMoviesItemModel(): MoviesItem {
        return movies.moviesList[0]
    }

    private fun getJson(path: String): String {
        val inputStream: InputStream = javaClass.classLoader?.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")

        return inputStream.bufferedReader().use(BufferedReader::readText)
    }
 }