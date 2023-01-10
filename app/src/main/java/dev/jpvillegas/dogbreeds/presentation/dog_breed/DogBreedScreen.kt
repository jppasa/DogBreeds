package dev.jpvillegas.dogbreeds.presentation.dog_breed

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.dogbreeds.R
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.domain.repository.RepositoryError

@Composable
fun DogBreedScreen(
    viewModel: DogBreedViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
) {
    val state = viewModel.state.value

    DogBreedContent(
        breedName = state.breedName,
        breedImages = state.breedImages,
        isLoading = state.isLoading,
        error = state.error,
        onFavoriteClicked = { dogImage ->
            viewModel.toggleDogImageFavorite(dogImage)
        },
        onBackClicked = onBackPressed
    )
}

@Composable
fun DogBreedContent(
    breedName: String,
    breedImages: List<DogImage>,
    isLoading: Boolean,
    error: RepositoryError,
    onFavoriteClicked: (DogImage) -> Unit,
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.go_back)
                        )
                    }

                    Text(
                        text = breedName.uppercase(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
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
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }

            if (error == RepositoryError.None && !isLoading) {
                if (breedImages.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_images_found),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        items(breedImages) {
                            DogImageItem(
                                url = it.imageUrl,
                                isFavorite = it.isFavorite,
                                dogBreedName = breedName,
                                showBreed = false,
                                onFavoriteClicked = { onFavoriteClicked(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}