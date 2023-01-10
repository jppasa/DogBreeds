package dev.jpvillegas.dogbreeds.domain.feature.dog_breed_list

import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetDogBreedsUseCase @Inject constructor(
    private val dogRepository: DogRepository
) {

    fun getDogBreedList(): Flow<FetchState<List<DogBreed>>> {
        return flow {
            emit(FetchState.Loading())
            dogRepository.getDogBreeds()?.let {
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
