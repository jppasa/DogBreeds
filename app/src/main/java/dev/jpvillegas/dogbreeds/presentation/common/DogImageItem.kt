package dev.jpvillegas.dogbreeds.presentation.dog_breed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.jpvillegas.dogbreeds.R
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@Composable
fun DogImageItem(
    url: String,
    isFavorite: Boolean,
    dogBreedName: String,
    showBreed: Boolean,
    onFavoriteClicked: () -> Unit
) {
    Box(
        modifier = Modifier.height(200.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(200)
                .build(),
            placeholder = painterResource(id = R.drawable.dog_placeholder),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(
                id = R.string.dog_breed_image_description,
                dogBreedName
            ),
            modifier = Modifier.fillMaxSize()
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .clickable { onFavoriteClicked() }
        ) {
            val (icon, descriptionId) = if (isFavorite) {
                Icons.Default.Favorite to R.string.add_to_favorites
            } else {
                Icons.Default.FavoriteBorder to R.string.remove_from_favorites
            }

            Image(
                imageVector = icon,
                contentDescription = stringResource(id = descriptionId),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )
        }

        if (showBreed) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(4.dp))
                    .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            ) {
                Text(
                    text = dogBreedName.capitalize(Locale.current),
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DogImageItemPreview() {
    DogBreedsTheme {
        DogImageItem(
            url = "",
            isFavorite = true,
            dogBreedName = "Mix breed",
            showBreed = true,
            onFavoriteClicked = {}
        )
    }
}
