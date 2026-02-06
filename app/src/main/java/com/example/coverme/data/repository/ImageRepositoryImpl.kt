package com.example.coverme.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.coverme.data.local.paging.UnSplashPagingSource
import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.data.remote.DTO.RandomPhotoDTO
import com.example.coverme.data.remote.api.UnSplashAPI
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(private val api: UnSplashAPI) : ImageRepository {
    override suspend fun getRandomImage(): Result<RandomPhotoDTO> {
        return try {
            val response = api.getRandomPhoto()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.Success(body)
                } else {
                    Log.d("HOHO", "Response body is null")
                    Result.Error("Response body is null")
                }
            } else {
                Log.d("HOHO", "Request failed with code ${response.code()}")
                Result.Error("Request failed with code ${response.code()}")
            }
        } catch (e: Exception) {
            Log.d("HOHO", "Error occur on random api ${e.message}")
            Result.Error("Error occur on random api ${e.message}")
        }
    }

    override fun getPhotos(): Flow<PagingData<PhotoDTOItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                maxSize = 400,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                UnSplashPagingSource(api)
            }
        ).flow
    }
}