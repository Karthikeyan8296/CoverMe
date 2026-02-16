package com.example.coverme.data.repository

import com.example.coverme.data.local.DAO.PhotosDAO
import com.example.coverme.data.local.entity.FavEntity
import com.example.coverme.domain.repository.StoreFavRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StoreFavRepositoryImpl @Inject constructor (
    private val dao: PhotosDAO
): StoreFavRepository {
    override suspend fun addToFav(id: String) {
        dao.insertPhoto(FavEntity(photoId = id))
    }

    override suspend fun removeFromFav(id: String) {
        dao.deletePhoto(id)
    }

    override suspend fun getAllFav(): Flow<List<String>> {
        return dao.getAllPhotos()
    }

    override suspend fun isFav(id: String): Boolean {
        return dao.isFav(id)
    }
}