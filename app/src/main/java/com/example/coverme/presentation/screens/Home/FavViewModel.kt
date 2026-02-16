package com.example.coverme.presentation.screens.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coverme.data.remote.DTO.PhotoDTO.PhotoDTOItem
import com.example.coverme.domain.models.PhotoModel
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.Result
import com.example.coverme.domain.repository.StoreFavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavViewModel @Inject constructor(
    private val favRepository: StoreFavRepository, private val imageRepository: ImageRepository
) : ViewModel() {

    private val _photos = MutableStateFlow<List<PhotoModel>>(emptyList())
    val photo = _photos.asStateFlow()

    fun loadFavPhotos() {
        viewModelScope.launch {
            favRepository.getAllFav().collectLatest { favId ->
                val result: List<PhotoModel> = favId.mapNotNull {
                    when (val res = imageRepository.getPhotoById(it)) {
                        is Result.Success -> res.data
                        is Result.Error -> {
                            Log.d("FavViewModel", res.message)
                            null
                        }
                    }
                }
                _photos.value = result
            }

        }
    }
}