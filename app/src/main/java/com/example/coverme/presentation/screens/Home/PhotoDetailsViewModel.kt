package com.example.coverme.presentation.screens.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.Result
import com.example.coverme.domain.repository.StoreFavRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PhotoDetailsState(
    val image: String = "",
    val loading: Boolean = false,
    val error: String = "",
    val isFav: Boolean = false,
    val name: String = "",
    val userName: String = "",
    val profileImage: String? = ""
)

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val repository: ImageRepository, private val storeFavRepository: StoreFavRepository
) : ViewModel() {

    private val _state = MutableStateFlow(PhotoDetailsState())
    val state = _state.asStateFlow()

    fun getImages(id: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)

            when (val result = repository.getPhotoById(id)) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        loading = false,
                        image = result.data.urls.regular,
                        name = result.data.user.name,
                        userName = result.data.user.username,
                        profileImage = result.data.user.profile_image?.medium
                    )
                    Log.d("PROFILE_IMAGE", result.data.user.profile_image?.small.toString())
                }

                is Result.Error -> {
                    _state.value = state.value.copy(
                        loading = false, error = result.message
                    )
                }
            }
        }
    }

    fun addToFav(id: String) {
        viewModelScope.launch {
            storeFavRepository.addToFav(id)
        }
    }

    fun removeFromFav(id: String) {
        viewModelScope.launch {
            storeFavRepository.removeFromFav(id)
        }
    }

    fun checkIsFav(id: String) {
        viewModelScope.launch {
            val fav = storeFavRepository.isFav(id)
            _state.value = _state.value.copy(isFav = fav)
        }
    }
}