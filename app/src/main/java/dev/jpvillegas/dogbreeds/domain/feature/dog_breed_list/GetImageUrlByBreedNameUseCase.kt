package dev.jpvillegas.dogbreeds.domain.feature.dog_breed_list

import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetImageUrlByBreedNameUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {

    fun getRandomImageUrl(dogBreedName: String): Flow<FetchState<String>> {
        return flow {
            emit(FetchState.Loading())
            dogRepository.getRandomImageByBreedName(dogBreedName)?.let {
                emit(FetchState.Success(data = it))
            } ?: emit(FetchState.Error(error = RepositoryError.NoData))
        }.catch { throwable ->
            if (throwable is IOException) {
                emit(FetchState.Error(error = RepositoryError.Network))
            } else {
                emit(FetchState.Error(error = RepositoryError.Unknown))
            }
        }
    }
}
