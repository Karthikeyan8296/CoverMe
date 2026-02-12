package com.example.coverme.domain.models

import com.example.coverme.data.remote.DTO.PhotoDTO.Urls
import com.example.coverme.data.remote.DTO.PhotoDTO.User

data class PhotoModel(
    val blur_hash: String,
    val color: String,
    val created_at: String,
    val description: String?,
    val height: Int,
    val id: String,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)