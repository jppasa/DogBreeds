package dev.jpvillegas.dogbreeds.presentation.favorites

import dev.jpvillegas.dogbreeds.domain.model.DogImage

data class FavoritesState(
    val isLoading: Boolean = true,
    val images: List<DogImage> = emptyList(),
    val dogBreedNames: List<String> = emptyList(),
    val selectedDogBreed: String = ""
)