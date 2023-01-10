package dev.jpvillegas.dogbreeds.data.repository

import dev.jpvillegas.dogbreeds.data.db.FavoriteDogImageDao
import dev.jpvillegas.dogbreeds.data.network.DogBreedsService
import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(
    private val dogBreedsService: DogBreedsService,
    private val favoriteDogImageDao: FavoriteDogImageDao,
) : DogRepository {

    override suspend fun getDogBreeds(): List<DogBreed>? {
        return dogBreedsService
            .getDogBreeds()
            .execute()
            .body()
            ?.message
            ?.map {
                DogBreed(it.key, variants = it.value)
            }
    }

    override suspend fun getDogImageUrlsByName(name: String): List<String>? {
        return dogBreedsService.getBreedImageUrls(name)
            .execute()
            .body()
            ?.message
    }

    override suspend fun getRandomImageByBreedName(name: String): String? {
        return dogBreedsService.getRandomDogImageByBreedName(name)
            .execute()
            .body()
            ?.message
    }

    override suspend fun isImageUrlFavorite(url: String): Boolean {
        return favoriteDogImageDao.getFavoriteByUrl(url) != null
    }

    override fun getFavoriteImagesFlow(): Flow<List<DogImage>> {
        return favoriteDogImageDao.getAllAsFlow().map { list ->
            list.map {
                DogImage(
                    dogBreedName = it.dogBreedName,
                    imageUrl = it.imageUrl,
                    isFavorite = true
                )
            }
        }
    }

    override suspend fun getFavoriteImagesByDogBreed(dogBreedName: String): List<DogImage> {
        return if (dogBreedName.isBlank()) {
            favoriteDogImageDao.getAll()
        } else {
            favoriteDogImageDao.getByBreedName(dogBreedName)
        }.map {
            DogImage(
                dogBreedName = it.dogBreedName,
                imageUrl = it.imageUrl,
                isFavorite = true
            )
        }
    }
}