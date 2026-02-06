package com.example.coverme.data.remote.DTO

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RandomPhotoDTO(
    val asset_type: String,
    val blur_hash: String,
    val bookmarked: Boolean,
    val breadcrumbs: List<Any>,
    val color: String,
    val created_at: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: String,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)