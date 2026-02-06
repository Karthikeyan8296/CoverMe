package com.example.coverme.data.local.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.coverme.data.local.entity.FavEntity

@Dao
interface PhotosDAO {

    @Insert
    suspend fun insertPhoto(photo: FavEntity)

    @Query("DELETE FROM FavPhotosTable WHERE photoId = :id")
    suspend fun deletePhoto(id: String)

    @Query("SELECT photoId FROM FavPhotosTable")
    suspend fun getAllPhotos(): List<String>

    @Query("SELECT EXISTS(SELECT 1 FROM FavPhotosTable WHERE photoId = :id)")
    suspend fun isFav(id: String): Boolean
}
