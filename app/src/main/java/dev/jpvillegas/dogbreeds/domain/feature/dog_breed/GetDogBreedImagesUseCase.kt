package dev.jpvillegas.dogbreeds.domain.feature.dog_breed

import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDogBreedImagesUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {

    fun getDogBreedImageUrls(dogBreedName: String): Flow<FetchState<List<DogImage>>> {
        return flow {
            emit(FetchState.Loading())
            dogRepository.getDogImageUrlsByName(dogBreedName)
                ?.map { url ->
                    val isFavorite = dogRepository.isImageUrlFavorite(url)
                    DogImage(
                        dogBreedName = dogBreedName,
                        imageUrl = url,
                        isFavorite = isFavorite
                    )
                }?.let {
                    emit(FetchState.Success(data = it))
                }
                ?: emit(FetchState.Error(error = RepositoryError.NoData))
        }.catch { throwable ->
            throwable.printStackTrace()
            if (throwable is IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } else {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }
    }
}