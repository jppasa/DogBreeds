package dev.jpvillegas.dogbreeds.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.jpvillegas.dogbreeds.data.db.AppDb.Companion.DATABASE_VERSION
import dev.jpvillegas.dogbreeds.data.db.entity.FavoriteDogImage

@Database(
    entities = [
        FavoriteDogImage::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {

    abstract fun favoriteDogImageDao(): FavoriteDogImageDao

    companion object {
        const val DATABASE_NAME = "dog_breeds_db"
        const val DATABASE_VERSION = 1
    }
}