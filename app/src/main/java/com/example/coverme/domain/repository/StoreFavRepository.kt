package com.example.coverme.domain.repository

import kotlinx.coroutines.flow.Flow

interface StoreFavRepository {
    suspend fun addToFav(id: String)

    suspend fun removeFromFav(id: String)

    suspend fun getAllFav(): Flow<List<String>>

    suspend fun isFav(id: String) : Boolean
}
