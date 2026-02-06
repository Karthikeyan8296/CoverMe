package com.example.coverme.domain.repository

interface StoreFavRepository {
    suspend fun addToFav(id: String)

    suspend fun removeFromFav(id: String)

    suspend fun getAllFav(): List<String>

    suspend fun isFav(id: String) : Boolean
}
