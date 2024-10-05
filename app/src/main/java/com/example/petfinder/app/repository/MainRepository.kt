package com.example.petfinder.app.repository

import com.example.petfinder.app.model.AdoptionRequest
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.service.MainService

class MainRepository constructor(
    private val mainService: MainService
){
    suspend fun getAllPets() = mainService.getAllPets()

    suspend fun updatePet(pet: Pet) = mainService.updatePet(pet)

    suspend fun postPet(pet: Pet) = mainService.createPet(pet)

    suspend fun postAdoptionRequest(adoptionRequest: AdoptionRequest) = mainService.createAdoptionRequest(adoptionRequest)

    suspend fun getMyPets() = mainService.getMyPets()

    suspend fun getSavedAdoptionRequests() = mainService.getSavedAdoptionRequests()

    suspend fun getMyAdoptionRequests() = mainService.getMyAdoptionRequests()

    suspend fun getPetById(id : Long) = mainService.getPetById(id)

    suspend fun deletePetById(id : Long) = mainService.deletePetById(id)

    suspend fun getUserByEmail(email : String) = mainService.getUserByEmail(email)

    suspend fun getAdoptionRequestById(id : Long) = mainService.getAdoptionRequestById(id)

    suspend fun getAllBreeds() = mainService.getAllBreeds()

    suspend fun getAllGenders() = mainService.getAllGenders()

    suspend fun getAllSizes() = mainService.getAllSizes()

    suspend fun getAllTypes() = mainService.getAllTypes()

    suspend fun aiRequestMessage(base: String) = mainService.aiRequestMessage(base)
}