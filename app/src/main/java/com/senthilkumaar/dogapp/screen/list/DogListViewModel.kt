package com.senthilkumaar.dogapp.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.senthilkumaar.dogapp.model.Dog
import com.senthilkumaar.dogapp.repository.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


data class DogListUiState(
    val isLoading: Boolean = false,
    val dogs: List<Dog> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class DogListViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DogListUiState())
    val uiState: StateFlow<DogListUiState> = _uiState.asStateFlow()

    init {
        fetchDogs()
    }

    private fun fetchDogs() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                repository.getBreeds().collect { dogsList ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            dogs = dogsList,
                            errorMessage = null
                        )
                    }
                }
            } catch (e: Exception) {
                Timber.e(e, "Error fetching dog breeds")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.localizedMessage ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun refreshDogs() {
        fetchDogs()
    }
}