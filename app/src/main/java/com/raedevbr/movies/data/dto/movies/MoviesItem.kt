package com.raedevbr.movies.data.dto.movies

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MoviesItem(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @SerializedName("title")
    @ColumnInfo(name = "title")
    var title: String = "",

    @SerializedName("overview")
    @ColumnInfo(name = "description")
    var description: String = "",

    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    var posterPath: String = "",

    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    var backdropPath: String = "",

    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",

    @SerializedName("vote_average")
    @ColumnInfo(name = "rating")
    var rating: Float = 0F,

    @SerializedName("popularity")
    @ColumnInfo(name = "popularity")
    var popularity: Float = 0F,

    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    var originalTitle: String = "",

    @Ignore
    @SerializedName("vote_count")
    var voteCount: Int = 0
) : Parcelable
