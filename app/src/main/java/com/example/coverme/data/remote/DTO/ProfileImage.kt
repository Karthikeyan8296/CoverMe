package com.example.coverme.data.remote.DTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)