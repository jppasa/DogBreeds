package dev.jpvillegas.dogbreeds.presentation.dog_breed_list

import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError

data class DogBreedListState(
    val isLoading: Boolean = false,
    val dogBreeds: List<DogBreed> = emptyList(),
    val randomImagesUrls : Map<String, String?> = mutableMapOf(),
    val error: RepositoryError = RepositoryError.None
)
