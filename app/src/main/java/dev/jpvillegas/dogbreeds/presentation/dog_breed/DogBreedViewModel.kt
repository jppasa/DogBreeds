package dev.jpvillegas.dogbreeds.presentation.dog_breed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.dogbreeds.domain.feature.dog_breed.FavoriteDogImageUseCase
import dev.jpvillegas.dogbreeds.domain.feature.dog_breed.GetDogBreedImagesUseCase
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.model.ToggleResult
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import dev.jpvillegas.dogbreeds.presentation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogBreedViewModel @Inject constructor(
    private val getDogBreedImagesUseCase: GetDogBreedImagesUseCase,
    private val favoriteDogImageUseCase: FavoriteDogImageUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf(SelectedDogBreedState())
    val state: State<SelectedDogBreedState> = _state

    init {
        savedStateHandle.get<String>(Screen.PARAM_DOB_BREED_ID)?.let {
            loadDogBreedImages(it)
        }
    }

    private fun loadDogBreedImages(dogBreedName: String) {
        _state.value = _state.value.copy(
            breedName = dogBreedName
        )

        getDogBreedImagesUseCase
            .getDogBreedImageUrls(dogBreedName)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is FetchState.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true,
                            error = RepositoryError.None
                        )
                    }
                    is FetchState.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            breedImages = emptyList(),
                            error = it.error
                        )
                    }
                    is FetchState.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            breedImages = it.data ?: emptyList(),
                            error = RepositoryError.None
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun toggleDogImageFavorite(dogImage: DogImage) {
        viewModelScope.launch {
            val result = favoriteDogImageUseCase.toggleFavoriteDogImage(dogImage)

            val list = _state.value.breedImages.toMutableList()
            when (result) {
                ToggleResult.INSERTED -> list.updateInPlace(dogImage, true)
                ToggleResult.DELETED -> list.updateInPlace(dogImage, false)
            }

            _state.value = _state.value.copy(
                breedImages = list//.toList()
            )
        }
    }
}

fun MutableList<DogImage>.updateInPlace(dogImage: DogImage, isFavorite: Boolean) {
    val index = indexOf(dogImage)
    removeAt(index)
    add(index, dogImage.copy(isFavorite = isFavorite))
}