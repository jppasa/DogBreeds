package dev.jpvillegas.dogbreeds.domain.feature.dog_breed

import dev.jpvillegas.dogbreeds.data.db.FavoriteDogImageDao
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.model.ToggleResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavoriteDogImageUseCase @Inject constructor(
    private val favoriteDogImageDao: FavoriteDogImageDao
) {

    suspend fun toggleFavoriteDogImage(dogImage: DogImage): ToggleResult {
        return withContext(Dispatchers.IO) {
            favoriteDogImageDao.toggleFavoriteImageByUrl(dogImage.imageUrl, dogImage.dogBreedName)
        }
    }
}

