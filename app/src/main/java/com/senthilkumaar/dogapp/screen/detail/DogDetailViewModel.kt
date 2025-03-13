package com.senthilkumaar.dogapp.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senthilkumaar.dogapp.model.Dog
import com.senthilkumaar.dogapp.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DogDetailUiState(
    val isLoading: Boolean = false,
    val dog: Dog? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class DogDetailViewModel @Inject constructor(
    private val repository: DogRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(DogDetailUiState())
    val uiState: StateFlow<DogDetailUiState> = _uiState.asStateFlow()

    init {
        val dogId: Int? = savedStateHandle["dogId"]
        dogId?.let { fetchDogDetails(it) }
    }

    private fun fetchDogDetails(dogId: Int) {
        _uiState.value = _uiState.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                repository.getBreeds().collect { dogs ->
                    val selectedDog = dogs.find { it.id == dogId }
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        dog = selectedDog,
                        errorMessage = if (selectedDog == null) "Dog not found" else null
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }
}