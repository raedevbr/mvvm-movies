package com.raedevbr.movies

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.raedevbr.movies.data.dto.movies.Movies
import java.io.InputStream

object TestUtil {
    var dataStatus: DataStatus = DataStatus.Success
    var movies: Movies = Movies(arrayListOf())
    fun initData(): Movies {
        val jsonString = getJson("MoviesApiResponse.json")
        val gson = Gson()

        movies = gson.fromJson(jsonString, Movies::class.java)
        return movies
    }

    private fun getJson(path: String): String {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}