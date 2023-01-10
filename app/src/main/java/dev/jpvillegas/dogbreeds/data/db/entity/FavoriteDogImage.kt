package dev.jpvillegas.dogbreeds.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoriteDogImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dogBreedName: String,
    val imageUrl: String,
)