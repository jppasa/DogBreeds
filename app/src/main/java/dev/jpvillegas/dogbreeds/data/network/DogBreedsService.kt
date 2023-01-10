package dev.jpvillegas.dogbreeds.data.network

import dev.jpvillegas.dogbreeds.data.network.model.BreedImageUrlsResponse
import dev.jpvillegas.dogbreeds.data.network.model.BreedRandomImageResponse
import dev.jpvillegas.dogbreeds.data.network.model.DogBreedListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DogBreedsService {

    @GET("breeds/list/all")
    fun getDogBreeds() : Call<DogBreedListResponse>

    @GET("breed/{breed}/images")
    fun getBreedImageUrls(@Path("breed") breedName: String) : Call<BreedImageUrlsResponse>

    @GET("breed/{breed}/{subBreed}/images")
    fun getSubBreedImageUrls(
        @Path("breed") breedName: String,
        @Path("subBreed") breedSubName: String,
    ) : Call<BreedImageUrlsResponse>

    @GET("breed/{breed}/images/random")
    fun getRandomDogImageByBreedName(@Path("breed") breedName: String): Call<BreedRandomImageResponse>
}