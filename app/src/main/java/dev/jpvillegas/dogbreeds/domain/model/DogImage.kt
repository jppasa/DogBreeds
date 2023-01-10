package dev.jpvillegas.dogbreeds.domain.model

data class DogImage(
    val dogBreedName: String,
    val imageUrl: String,
    val isFavorite: Boolean
)