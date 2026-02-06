package com.example.coverme.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavPhotosTable")
data class FavEntity(
    @PrimaryKey
    val photoId : String
)
