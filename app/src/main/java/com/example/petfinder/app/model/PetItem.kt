package com.example.petfinder.app.model

sealed class PetItem {
    data class PetType(val pet: Pet) : PetItem()
    data class AdoptionRequestType(val adoptionRequest: AdoptionRequest) : PetItem()
}