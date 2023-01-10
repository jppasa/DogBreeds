package dev.jpvillegas.dogbreeds.presentation.dog_breed_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.dogbreeds.R
import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@Composable
fun DogBreedsScreen(
    onDogBreedClicked: (DogBreed) -> Unit,
    onFavoritesClicked: () -> Unit,
    viewModel: DogBreedListViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    DogBreedListContent(
        dogBreedList = state.dogBreeds,
        dogBreedImageUrls = state.randomImagesUrls,
        isLoading = state.isLoading,
        error = state.error,
        onRequestDogRandomImageUrl = { dogBreedName ->
            viewModel.fetchRandomImageFromBreed(dogBreedName)
        },
        onItemClicked = onDogBreedClicked,
        onFavoritesClicked = onFavoritesClicked
    )
}

@Composable
fun DogBreedListContent(
    dogBreedList: List<DogBreed>,
    dogBreedImageUrls: Map<String, String?>,
    isLoading: Boolean,
    error: RepositoryError,
    onRequestDogRandomImageUrl: (String) -> Unit,
    onItemClicked: (DogBreed) -> Unit,
    onFavoritesClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.dog_breeds_title).uppercase(),
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.weight(1f)
                    )

                    OutlinedButton(
                        onClick = onFavoritesClicked,
                        border = null,
                        modifier = Modifier.width(40.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Image(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = stringResource(id = R.string.go_to_favorites),
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                        )
                    }
                }
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AnimatedVisibility(
                visible = isLoading,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                CircularProgressIndicator()
            }

            AnimatedVisibility(
                visible = error != RepositoryError.None,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                val errorStr = when (error) {
                    RepositoryError.Network -> stringResource(id = R.string.network_error)
                    RepositoryError.Unknown -> stringResource(id = R.string.unknown_error)
                    RepositoryError.NoData -> stringResource(id = R.string.no_data)
                    RepositoryError.None -> ""
                }

                Text(
                    text = errorStr,
                    style = MaterialTheme.typography.body2
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {

                items(dogBreedList) {
                    DogBreedItem(
                        dogBreed = it,
                        imageUrl = dogBreedImageUrls[it.name],
                        onRequestImageUrlDownload = onRequestDogRandomImageUrl
                    ) {
                        onItemClicked(it)
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun DogsBreedScreenPreview() {
    DogBreedsTheme {
        val list = listOf(
            DogBreed("Chapin", emptyList()),
            DogBreed("Mezclita", emptyList())
        )
        DogBreedListContent(
            dogBreedList = list,
            dogBreedImageUrls = emptyMap(),
            isLoading = false,
            error = RepositoryError.None,
            onRequestDogRandomImageUrl = { },
            onItemClicked = {},
            onFavoritesClicked = {}
        )
    }
}