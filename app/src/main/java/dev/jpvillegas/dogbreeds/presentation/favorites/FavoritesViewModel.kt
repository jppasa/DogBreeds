package dev.jpvillegas.dogbreeds.presentation.favorites

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.dogbreeds.domain.feature.dog_breed.FavoriteDogImageUseCase
import dev.jpvillegas.dogbreeds.domain.feature.favorites.GetFavoriteImagesUseCase
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteDogImageUseCase: FavoriteDogImageUseCase,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
) : ViewModel() {

    private var selectedDogBreedName: String = ""

    private val _state = mutableStateOf(FavoritesState())
    val state: State<FavoritesState> = _state

    init {
        loadDogBreedNames()
        loadImages()
    }

    private fun loadDogBreedNames() {
        getFavoriteImagesUseCase
            .getDogBreedNames()
            .flowOn(Dispatchers.IO)
            .onEach {
                _state.value = _state.value.copy(
                    dogBreedNames = it
                )
            }.launchIn(viewModelScope)
    }

    private fun loadImages(dogBreedName: String = "") {
        selectedDogBreedName = if (selectedDogBreedName == dogBreedName) "" else dogBreedName

        getFavoriteImagesUseCase
            .getDogBreedImageUrls(selectedDogBreedName)
            .flowOn(Dispatchers.IO)
            .onEach { fetchState ->
                _state.value = _state.value.copy(
                    isLoading = false,
                    images = fetchState,
                    selectedDogBreed = selectedDogBreedName
                )
            }.launchIn(viewModelScope)
    }

    fun toggleDogImageFavorite(dogImage: DogImage) {
        viewModelScope.launch {
            favoriteDogImageUseCase.toggleFavoriteDogImage(dogImage)
        }
    }

    fun toggleSelectedDogBreed(dogBreedName: String) {
        loadImages(dogBreedName)
    }
}

