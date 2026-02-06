package com.example.coverme.presentation.screens.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.coverme.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class HomeState(
    val isLoading: Boolean = false, val image: String? = null, val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    val photos = imageRepository.getPhotos().cachedIn(viewModelScope)
}