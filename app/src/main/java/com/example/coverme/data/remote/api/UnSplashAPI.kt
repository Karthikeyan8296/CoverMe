package com.example.coverme.data.remote.api

import com.example.coverme.data.remote.DTO.RandomPhotoDTO
import retrofit2.Response
import retrofit2.http.GET

interface UnSplashAPI {

    @GET("photos/random")
    suspend fun getRandomPhoto(): Response<RandomPhotoDTO>
}