package com.example.coverme.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coverme.data.local.DAO.PhotosDAO
import com.example.coverme.data.local.entity.FavEntity


@Database(
    entities = [FavEntity::class], version = 5, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDAO
}