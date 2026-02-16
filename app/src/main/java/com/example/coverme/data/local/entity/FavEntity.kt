package com.example.coverme.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavPhotosTable")
data class FavEntity(
    @PrimaryKey(autoGenerate = true)
    val favId: Int = 0,
    val photoId : String
)
