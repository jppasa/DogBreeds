package dev.jpvillegas.dogbreeds.presentation

sealed class Screen(val route: String) {
    object DogBreedListScreen : Screen("dog_breed_list_screen")
    object DogBreedScreen : Screen("dob_breed_screen")
    object FavoritesScreen : Screen("favorites_screen")

    companion object {
        const val PARAM_DOB_BREED_ID = "breedId"
    }
}
