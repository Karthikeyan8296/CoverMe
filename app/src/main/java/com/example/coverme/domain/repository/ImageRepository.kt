package com.example.coverme.domain.repository

import com.example.coverme.data.remote.DTO.RandomPhotoDTO

interface ImageRepository {
    suspend fun getRandomImage() : Result<RandomPhotoDTO>
}

sealed class Result<T>{
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
}