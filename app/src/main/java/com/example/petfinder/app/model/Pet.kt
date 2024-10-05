package com.example.petfinder.app.model

import java.util.*
data class Pet(
    val id: Long = 0L,
    val description: String,
    val name: String,
    val imageName: String,
    val color: String,
    val specialCare: String,
    val birthDate: Any,
    val size: Size,
    val gender: Gender,
    val user: Users,
    val type: Type,
    val breed: Breed,
)