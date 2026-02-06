package com.example.coverme.data.remote.DTO.PhotoDTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)