package dev.jpvillegas.dogbreeds.presentation.dog_breed_list

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.jpvillegas.dogbreeds.R
import dev.jpvillegas.dogbreeds.domain.model.DogBreed
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DogBreedItem(
    dogBreed: DogBreed,
    imageUrl: String?,
    onRequestImageUrlDownload: (String) -> Unit,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            if (imageUrl != null) {
                val context = LocalContext.current
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(durationMillis = 200)
                        .build(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.dog_placeholder),
                    contentDescription = dogBreed.name,
                    onError = {
                        Toast.makeText(
                            context,
                            "Error loading image of $dogBreed",
                            Toast.LENGTH_LONG
                        ).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.dog_placeholder),
                    contentDescription = dogBreed.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            Text(
                text = dogBreed.name.capitalize(Locale.current),
                modifier = Modifier.padding(16.dp)
            )
        }
    }

    LaunchedEffect(imageUrl) {
        if (imageUrl == null) {
            onRequestImageUrlDownload(dogBreed.name)
        }
    }
}

@Preview
@Composable
fun DogBreedItemPreview() {
    val item = DogBreed(
        name = "Mix Breed",
        variants = emptyList()
    )
    DogBreedsTheme {
        DogBreedItem(
            dogBreed = item,
            imageUrl = "",
            onRequestImageUrlDownload = { }
        ) {
            println("Dog breed: $item")
        }
    }
}