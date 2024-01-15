package com.raedevbr.movies.data.dto.movies

import com.google.gson.annotations.SerializedName

data class Movies(
   @SerializedName("results")
    val moviesList: MutableList<MoviesItem>
)
