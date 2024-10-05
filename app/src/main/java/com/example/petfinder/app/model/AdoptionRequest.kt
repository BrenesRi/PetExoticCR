package com.example.petfinder.app.model

data class AdoptionRequest(
    val adoptionMessage: String,
    val id: Long = 0L,
    val isAdopted: Boolean,
    val pet: Pet,
    val user: Users,
)
