package dev.jpvillegas.dogbreeds.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.jpvillegas.dogbreeds.data.db.AppDb
import dev.jpvillegas.dogbreeds.data.db.FavoriteDogImageDao
import dev.jpvillegas.dogbreeds.data.network.DogBreedsService
import dev.jpvillegas.dogbreeds.data.repository.DogRepositoryImpl
import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRetrofitService(): DogBreedsService {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(DogBreedsService::class.java)
    }

    @Provides
    @Singleton
    fun providesDogRepository(
        dogBreedsService: DogBreedsService,
        favoriteDogImageDao: FavoriteDogImageDao
    ): DogRepository {
        return DogRepositoryImpl(dogBreedsService, favoriteDogImageDao)
    }

    @Provides
    @Singleton
    fun providesFavoriteDogImageDao(appDb: AppDb): FavoriteDogImageDao {
        return appDb.favoriteDogImageDao()
    }

    @Provides
    @Singleton
    fun providesAppDb(@ApplicationContext appContext: Context): AppDb {
        return Room.databaseBuilder(
            appContext,
            AppDb::class.java,
            AppDb.DATABASE_NAME
        ).build()
    }
}