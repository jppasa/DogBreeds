package dev.jpvillegas.dogbreeds.domain.feature.dog_breed_list

import dev.jpvillegas.dogbreeds.domain.repository.DogRepository
import dev.jpvillegas.dogbreeds.domain.repository.FetchState
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
internal class GetDogBreedsUseCaseTest {

    private lateinit var getDogBreedsUseCase: GetDogBreedsUseCase
    private lateinit var repository: DogRepository

    @Before
    fun setUp() {
        repository = mock(DogRepository::class.java)
        getDogBreedsUseCase = GetDogBreedsUseCase(repository)
    }

    @Test
    fun `Start dog breed list fetch as loading state`() = runTest {
        val flow = getDogBreedsUseCase.getDogBreedList()
        val loadingState = flow.first()
        assertThat(loadingState, IsInstanceOf.instanceOf(FetchState.Loading::class.java))
    }

    @Test
    fun `Get dog breed list fetch as successful`() = runTest {
        Mockito.`when`(repository.getDogBreeds()).thenReturn(emptyList())

        val flow = getDogBreedsUseCase.getDogBreedList()
        val successState = flow.toList()[1]

        assertThat(successState, IsInstanceOf.instanceOf(FetchState.Success::class.java))
    }

    @Test
    fun `Get dog breed list fetch with network error`() = runTest {
        Mockito.`when`(repository.getDogBreeds()).thenThrow(IOException())

        val flow = getDogBreedsUseCase.getDogBreedList()
        val networkError = flow.toList()[1]

        assertThat(networkError, IsInstanceOf.instanceOf(FetchState.Error::class.java))
        assertEquals(networkError.error, RepositoryError.Network)
    }

    @Test
    fun `Get dog breed list fetch with unknown error`() = runTest {
        Mockito.`when`(repository.getDogBreeds()).thenThrow(Exception())

        val flow = getDogBreedsUseCase.getDogBreedList()
        val networkError = flow.toList()[1]

        assertThat(networkError, IsInstanceOf.instanceOf(FetchState.Error::class.java))
        assertEquals(networkError.error, RepositoryError.Unknown)
    }
}