package com.example.coverme.data.remote.DTO.PhotoDTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Links(
    val html: String,
    val photos: String,
    val portfolio: String,
    val self: String
)