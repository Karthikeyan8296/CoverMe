package com.example.coverme.presentation.screens.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coverme.domain.repository.ImageRepository
import com.example.coverme.domain.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RandomImageState(
    val isLoading: Boolean = false, val image: String? = null, val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val imageRepository: ImageRepository) :
    ViewModel() {
    private val _state = MutableStateFlow(RandomImageState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            when (val result = imageRepository.getRandomImage()) {
                is Result.Success -> {
                    _state.value =
                        state.value.copy(isLoading = false, image = result.data.urls.full)

                    Log.d("LOLLLL", "the value ${result.data}")
                }

                is Result.Error -> {
                    _state.value = state.value.copy(
                        isLoading = false, image = "", error = result.message
                    )

                    Log.d("HOHO", result.message)
                }
            }
        }
    }

}