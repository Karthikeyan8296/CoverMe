package com.example.coverme.data.remote.api

import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnSplashAPI {

    @GET("photos/random")
    suspend fun getRandomPhoto(): Response<PhotoDTOItem>

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): List<PhotoDTOItem>

    @GET("/photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String?
    ): Response<PhotoDTOItem>
}