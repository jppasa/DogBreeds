package dev.jpvillegas.dogbreeds.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jpvillegas.dogbreeds.presentation.dog_breed.DogBreedScreen
import dev.jpvillegas.dogbreeds.presentation.dog_breed_list.DogBreedsScreen
import dev.jpvillegas.dogbreeds.presentation.favorites.FavoritesScreen
import dev.jpvillegas.dogbreeds.presentation.ui.theme.DogBreedsTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.DogBreedListScreen.route
                    ) {
                        composable(
                            route = Screen.DogBreedListScreen.route
                        ) {
                            DogBreedsScreen(
                                onDogBreedClicked = { dogBreed ->
                                    navController.navigate(Screen.DogBreedScreen.route + "/${dogBreed.name}")
                                },
                                onFavoritesClicked = {
                                    navController.navigate(Screen.FavoritesScreen.route)
                                }
                            )
                        }

                        composable(
                            route = Screen.DogBreedScreen.route + "/{${Screen.PARAM_DOB_BREED_ID}}"
                        ) {
                            DogBreedScreen(
                                onBackPressed = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            route = Screen.FavoritesScreen.route
                        ) {
                            FavoritesScreen(
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


