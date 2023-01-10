package dev.jpvillegas.dogbreeds.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import dev.jpvillegas.dogbreeds.data.db.entity.FavoriteDogImage
import dev.jpvillegas.dogbreeds.domain.model.ToggleResult
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDogImageDao {

    @Insert
    fun insert(dogImage: FavoriteDogImage): Long

    @Delete
    fun delete(dogImage: FavoriteDogImage)

    @Query("SELECT * FROM FavoriteDogImage WHERE dogBreedName = :breedName")
    fun getByBreedName(breedName: String): List<FavoriteDogImage>

    @Query("SELECT * FROM FavoriteDogImage")
    fun getAll(): List<FavoriteDogImage>

    @Query("SELECT * FROM FavoriteDogImage")
    fun getAllAsFlow(): Flow<List<FavoriteDogImage>>

    @Query("SELECT * FROM FavoriteDogImage WHERE imageUrl = :url")
    fun getFavoriteByUrl(url: String): FavoriteDogImage?

    @Transaction
    fun toggleFavoriteImageByUrl(imageUrl: String, dogBreedName: String) : ToggleResult {
        val currentImage = getFavoriteByUrl(imageUrl)
        return if (currentImage == null) {
            insert(
                FavoriteDogImage(
                    id = 0,
                    imageUrl = imageUrl,
                    dogBreedName = dogBreedName
                )
            )
            ToggleResult.INSERTED
        } else {
            delete(currentImage)
            ToggleResult.DELETED
        }
    }
}