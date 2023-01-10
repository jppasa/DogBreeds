package dev.jpvillegas.dogbreeds.presentation.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.jpvillegas.dogbreeds.R
import dev.jpvillegas.dogbreeds.domain.model.DogImage
import dev.jpvillegas.dogbreeds.presentation.dog_breed.DogImageItem
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@Composable
fun FavoritesScreen(
    onBackClicked: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
//    val imagesState = viewModel.images.collectAsState(initial = null)
//    val dogBreedNames = viewModel.dogBreedNames.collectAsState(initial = emptyList())
//    val selectedDogBreed = viewModel.selectedDogBreed
    val state = viewModel.state.value

    FavoritesContent(
        images = state.images,
        dogBreedNames = state.dogBreedNames,
        selectedDogBreed = state.selectedDogBreed,
        onBackClicked = onBackClicked,
        onFavoriteClicked = { dogImage ->
            viewModel.toggleDogImageFavorite(dogImage)
        },
        onFilterClicked = { dogBreedName ->
            viewModel.toggleSelectedDogBreed(dogBreedName)
        }
    )
}

@Composable
fun FavoritesContent(
    images: List<DogImage>?,
    dogBreedNames: List<String>,
    selectedDogBreed: String,
    onBackClicked: () -> Unit,
    onFavoriteClicked: (DogImage) -> Unit,
    onFilterClicked: (String) -> Unit,
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
                        text = stringResource(id = R.string.favorites).uppercase(),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier
                            .weight(1f)
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
                visible = images == null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 32.dp)
            ) {
                CircularProgressIndicator()
            }
            if (dogBreedNames.size > 1) {
                Spacer(modifier = Modifier.height(16.dp))

                LazyRow {
                    item {
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    items(dogBreedNames) {
                        SelectablePill(
                            content = it,
                            isSelected = it == selectedDogBreed,
                            modifier = Modifier.padding(horizontal = 4.dp),
                            onClick = {
                                onFilterClicked(it)
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (images != null) {
                if (images.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(id = R.string.no_favorites_yet),
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                    ) {
                        items(images) {
                            DogImageItem(
                                url = it.imageUrl,
                                isFavorite = it.isFavorite,
                                dogBreedName = it.dogBreedName,
                                showBreed = true,
                                onFavoriteClicked = { onFavoriteClicked(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    DogBreedsTheme {
        FavoritesContent(
            images = listOf(
                DogImage(
                    dogBreedName = "Mix",
                    imageUrl = "",
                    isFavorite = true
                ),
                DogImage(
                    dogBreedName = "Akita",
                    imageUrl = "",
                    isFavorite = true
                ),
                DogImage(
                    dogBreedName = "Corgi",
                    imageUrl = "",
                    isFavorite = true
                ),
                DogImage(
                    dogBreedName = "Bulldog",
                    imageUrl = "",
                    isFavorite = true
                ),
            ),
            dogBreedNames = listOf("Akita", "Bulldog", "Corgi", "Mix"),
            selectedDogBreed = "Mix",
            onBackClicked = {},
            onFavoriteClicked = {},
            onFilterClicked = {}
        )
    }
}
