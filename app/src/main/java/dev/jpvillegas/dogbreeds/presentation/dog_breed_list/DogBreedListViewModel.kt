package dev.jpvillegas.dogbreeds.presentation.dog_breed_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jpvillegas.dogbreeds.domain.feature.dog_breed_list.GetDogBreedsUseCase
import dev.jpvillegas.dogbreeds.domain.feature.dog_breed_list.GetImageUrlByBreedNameUseCase
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DogBreedListViewModel @Inject constructor(
    private val getDogBreedsUseCase: GetDogBreedsUseCase,
    private val getImageUrlByBreedNameUseCase: GetImageUrlByBreedNameUseCase
) : ViewModel() {

    private val _state = mutableStateOf(DogBreedListState())
    val state: State<DogBreedListState> = _state

    private val _randomImageUrls = mutableMapOf<String, String?>()

    init {
        // TODO launch from UI
        loadDogBreeds()
    }

    private fun loadDogBreeds() {
        getDogBreedsUseCase
            .getDogBreedList()
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
                            dogBreeds = emptyList(),
                            error = it.error
                        )
                    }
                    is FetchState.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            dogBreeds = it.data ?: emptyList(),
                            error = RepositoryError.None
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun fetchRandomImageFromBreed(dogBreedName: String) {
        getImageUrlByBreedNameUseCase
            .getRandomImageUrl(dogBreedName)
            .flowOn(Dispatchers.IO)
            .onEach {
                when (it) {
                    is FetchState.Loading -> {}
                    is FetchState.Error -> {
                        _randomImageUrls[dogBreedName] = null
                    }
                    is FetchState.Success -> {
                        _randomImageUrls[dogBreedName] = it.data
                    }
                }

                _state.value = _state.value.copy(
                    randomImagesUrls = _randomImageUrls.toMap()
                )
            }.launchIn(viewModelScope)

    }
}