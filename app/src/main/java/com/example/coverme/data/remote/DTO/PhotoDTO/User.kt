package com.example.coverme.data.remote.DTO.PhotoDTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    val bio: String?,
    val id: String,
    val instagram_username: String?,
    val links: Links?,
    val location: String?,
    val name: String,
    val portfolio_url: String?,
    val profile_image: ProfileImage?,
    val total_collections: Int?,
    val twitter_username: String?,
    val username: String
)