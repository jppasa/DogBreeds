package dev.jpvillegas.dogbreeds.domain.repository

import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import kotlinx.coroutines.flow.Flow
import java.io.IOException

interface DogRepository {

    @Throws(IOException::class, Exception::class)
    suspend fun getDogBreeds() : List<DogBreed>?

    @Throws(IOException::class, Exception::class)
    suspend fun getDogImageUrlsByName(name: String) : List<String>?

    @Throws(IOException::class, Exception::class)
    suspend fun getRandomImageByBreedName(name: String) : String?

    suspend fun isImageUrlFavorite(url: String) : Boolean

    fun getFavoriteImagesFlow() : Flow<List<DogImage>>

    suspend fun getFavoriteImagesByDogBreed(dogBreedName: String) : List<DogImage>
}