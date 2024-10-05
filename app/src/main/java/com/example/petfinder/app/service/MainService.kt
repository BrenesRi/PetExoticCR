package com.example.petfinder.app.service

import android.accounts.AuthenticatorDescription
import com.example.petfinder.app.model.AdoptionRequest
import com.example.petfinder.app.model.Breed
import com.example.petfinder.app.model.Gender
import com.example.petfinder.app.model.Message
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.Size
import com.example.petfinder.app.model.Type
import com.example.petfinder.app.model.Users
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MainService {

    @GET("v1/pets")
    suspend fun getAllPets() : Response<List<Pet>>

    @PUT("v1/pets")
    suspend fun updatePet(@Body pet: Pet) : Response<Void>

    @POST("v1/pets")
    suspend fun createPet(@Body pet: Pet): Response<Void>

    @POST("v1/adoptions")
    suspend fun createAdoptionRequest(@Body adoptionRequest: AdoptionRequest): Response<Void>

    @GET("v1/pets/users/me")
    suspend fun getMyPets() : Response<List<Pet>>

    @GET("v1/adoptions/users/like/me")
    suspend fun getSavedAdoptionRequests() : Response<List<AdoptionRequest>>

    @GET("v1/adoptions/users/me")
    suspend fun getMyAdoptionRequests() : Response<List<AdoptionRequest>>

    @GET("v1/pets/{id}")
    suspend fun getPetById(@Path("id") id: Long) : Response<Pet>

    @DELETE("v1/pets/{id}")
    suspend fun deletePetById(@Path("id") id: Long) : Response<Void>

    @GET("v1/users/{email}")
    suspend fun getUserByEmail(@Path("email") email: String) : Response<Users>

    @GET("v1/adoptions/{id}")
    suspend fun getAdoptionRequestById(@Path("id") id: Long) : Response<AdoptionRequest>

    @GET("v1/breeds")
    suspend fun getAllBreeds() : Response<List<Breed>>

    @GET("v1/genders")
    suspend fun getAllGenders(): Response<List<Gender>>

    @GET("v1/sizes")
    suspend fun getAllSizes(): Response<List<Size>>

    @GET("v1/types")
    suspend fun getAllTypes(): Response<List<Type>>

    @GET("v1/chat/{base}")
    suspend fun aiRequestMessage(@Path("base") base: String): Response<Message>

    companion object{
        var mainService : MainService? = null
        fun getInstance() : MainService {
            if (mainService == null) {
                mainService = ServiceBuilder.buildService(MainService::class.java)
//                val retrofit = Retrofit.Builder()
//                    .baseUrl("http://192.168.100.30:3001/")
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//                mainService = retrofit.create(MainService::class.java)
            }
            return mainService!!
        }
    }
}