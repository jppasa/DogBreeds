package dev.jpvillegas.dogbreeds.data.network.model

data class DogBreedListResponse(
  val status: String,
  val message: Map<String, List<String>>?
)

