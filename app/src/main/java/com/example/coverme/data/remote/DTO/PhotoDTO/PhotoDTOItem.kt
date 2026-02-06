package com.example.coverme.data.remote.DTO.PhotoDTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoDTOItem(
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