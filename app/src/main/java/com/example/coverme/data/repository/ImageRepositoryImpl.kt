package com.example.coverme.data.repository

import android.util.Log
import com.example.coverme.data.remote.DTO.RandomPhotoDTO
import com.example.coverme.data.remote.api.UnSplashAPI
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.Result
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor (private val api: UnSplashAPI): ImageRepository {
    override suspend fun getRandomImage(): Result<RandomPhotoDTO> {
        return try{
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
        }catch (e: Exception){
            Log.d("HOHO", "Error occur on random api ${e.message}")
            Result.Error("Error occur on random api ${e.message}")
        }
    }
}