package com.raedevbr.movies.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raedevbr.movies.data.dto.movies.MoviesItem
import com.raedevbr.movies.data.local.MovieDao

@Database(entities = [MoviesItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        val DATABASE_NAME = "movie_db"
    }
}