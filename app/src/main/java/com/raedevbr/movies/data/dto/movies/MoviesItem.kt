package com.raedevbr.movies.data.dto.movies

import android.os.Parcelable
import androidx.room.Ignore
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesItem(
    @SerializedName("id")
    val id: Long = 0,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("overview")
    val description: String = "",

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("backdrop_path")
    val backdropPath: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("vote_average")
    val rating: Float = 0F,

    @SerializedName("popularity")
    val popularity: Float = 0F,

    @SerializedName("original_title")
    val originalTitle: String = "",

    @Ignore
    @SerializedName("vote_count")
    val voteCount: Int = 0
) : Parcelable
