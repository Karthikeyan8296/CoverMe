package com.example.coverme.data.remote.DTO.PhotoDTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)