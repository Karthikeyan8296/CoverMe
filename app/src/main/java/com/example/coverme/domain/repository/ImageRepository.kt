package com.example.coverme.domain.repository

import androidx.paging.PagingData
import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.data.remote.DTO.RandomPhotoDTO
import com.example.coverme.domain.models.PhotoModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getRandomImage(): Result<PhotoModel>

    fun getPhotos(): Flow<PagingData<PhotoModel>>

    suspend fun getPhotoById(id: String?): Result<PhotoModel>
}

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String) : Result<T>()
}