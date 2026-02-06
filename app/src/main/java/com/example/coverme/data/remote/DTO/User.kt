package com.example.coverme.data.remote.DTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val bio: String,
    val first_name: String,
    val id: String,
    val last_name: String,
    val name: String,
    val profile_image: ProfileImage,
    val updated_at: String,
    val username: String
)