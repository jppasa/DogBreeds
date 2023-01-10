package dev.jpvillegas.dogbreeds.presentation.dog_breed

import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError

data class SelectedDogBreedState(
    val isLoading: Boolean = false,
    val breedName: String = "",
    val breedImages: List<DogImage> = emptyList(),
    val error: RepositoryError = RepositoryError.None
)
