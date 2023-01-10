package dev.jpvillegas.dogbreeds.domain.feature.favorites

import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFavoriteImagesUseCase @Inject constructor(
    dogRepository: DogRepository
) {
    private val allImages = dogRepository.getFavoriteImagesFlow()

    fun getDogBreedNames(): Flow<List<String>> {
        return allImages.map { list ->
            list.map { it.dogBreedName }
                .distinct()
                .sorted()
        }
    }

    fun getDogBreedImageUrls(dogBreedName: String): Flow<List<DogImage>> {
        return if (dogBreedName.isBlank()) {
            allImages
        } else {
            allImages.map { list ->
                list.filter { it.dogBreedName == dogBreedName }
                    .ifEmpty { list }
            }
        }
    }
}