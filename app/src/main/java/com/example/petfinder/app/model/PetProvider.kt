package com.example.petfinder.app.model

import java.time.LocalDate
import java.util.*

/**
 * This is a temporary class to simulate the interaction with
 * data
 */
class PetProvider {
    companion object {
        /*fun findPetById(id: Int): Pet {
            return petList[id]
        }

        fun findAllPet(): List<Pet> {
            return petList
        }*/

        // Ejemplos de instancias de la clase Gender
        val maleGender = Gender(1, "Male")
        val femaleGender = Gender(2, "Female")

        // Ejemplos de instancias de la clase Role
        val role1 = Role(1, "Admin")
        val role2 = Role(2, "User")

        // Ejemplos de instancias de la clase Province
        val province1 = Province(1, "Heredia")
        val province2 = Province(2, "San José")

        // Ejemplos de instancias de la clase Users con las relaciones a otras clases
        val user1 = Users(1, "Juan", "+123456789", true, false, "juan@example.com", role1, province1)
        val user2 = Users(2, "Maria", "+987654321", true, false, "maria@example.com", role2, province2)
        // Ejemplos de instancias de la clase Type
        val type1 = Type(1, "Dog")
        val type2 = Type(2, "Cat")

        // Ejemplos de instancias de la clase Breed
        val breed1 = Breed(1, "Labrador")
        val breed2 = Breed(2, "Persian")

        val birthDate = Date(2015, 5, 15)
        val birthDate2 = Date(2017, 3, 22)

        /*val petList = listOf(
            Pet(1, "Friendly dog", "Buddy", "Medium", "buddy.jpg", "Golden", "Needs regular exercise", maleGender, birthDate2, user1, type1, breed1),
            Pet(2, "Shy cat", "Whiskers", "Small", "whiskers.jpg", "White", "Needs quiet environment", femaleGender, birthDate, user2, type2, breed2),
            Pet(3, "Energetic puppy", "Max", "Large", "max.jpg", "Black", "Loves playing fetch", maleGender, birthDate2, user1, type1, breed1),
            Pet(4, "Calm kitten", "Mittens", "Small", "mittens.jpg", "Gray", "Likes napping", femaleGender, birthDate, user2, type2, breed2),
            Pet(5, "Playful dog", "Rocky", "Medium", "rocky.jpg", "Brown", "Enjoys outdoor activities", maleGender, birthDate2, user1, type1, breed1),
            Pet(6, "Independent cat", "Sassy", "Small", "sassy.jpg", "Calico", "Prefers solitude", femaleGender, birthDate, user2, type2, breed2),
            Pet(7, "Cuddly dog", "Charlie", "Large", "charlie.jpg", "White", "Loves snuggling", maleGender, birthDate2, user1, type1, breed1),
            Pet(8, "Curious kitten", "Leo", "Small", "leo.jpg", "Orange", "Always exploring", maleGender, birthDate, user2, type2, breed2),
            Pet(9, "Protective dog", "Bella", "Medium", "bella.jpg", "Black", "Very loyal", femaleGender, birthDate2, user1, type1, breed1),
            Pet(10, "Gentle cat", "Luna", "Small", "luna.jpg", "Gray", "Affectionate", femaleGender, birthDate, user2, type2, breed2),
            Pet(11, "Adventurous dog", "Duke", "Large", "duke.jpg", "Brown", "Thrives on outdoor hikes", maleGender, birthDate2, user1, type1, breed1),
            Pet(12, "Mischievous kitten", "Oreo", "Small", "oreo.jpg", "Black and white", "Likes to play pranks", maleGender, birthDate, user2, type2, breed2),
            Pet(13, "Glamorous dog", "Princess", "Medium", "princess.jpg", "Pink", "Enjoys dressing up", femaleGender, birthDate2, user1, type1, breed1),
            Pet(14, "Elegant cat", "Sophie", "Small", "sophie.jpg", "White", "Graceful and poised", femaleGender, birthDate, user2, type2, breed2),
            Pet(15, "Spirited dog", "Cooper", "Large", "cooper.jpg", "Red", "Lively and playful", maleGender, birthDate2, user1, type1, breed1),
            Pet(16, "Affectionate kitten", "Milo", "Small", "milo.jpg", "Tabby", "Loves cuddling", maleGender, birthDate, user2, type2, breed2),
            Pet(17, "Brave dog", "Rex", "Medium", "rex.jpg", "Black and tan", "Fearless and protective", maleGender, birthDate2, user1, type1, breed1),
            Pet(18, "Sleek cat", "Cleo", "Small", "cleo.jpg", "Siamese", "Graceful and sleek", femaleGender, birthDate2, user2, type2, breed2),
            Pet(19, "Loyal dog", "Bailey", "Large", "bailey.jpg", "Golden", "Devoted companion", maleGender, birthDate2, user1, type1, breed1),
            Pet(20, "Charming kitten", "Simba", "Small", "simba.jpg", "Orange", "Charismatic and playful", maleGender, birthDate2, user2, type2, breed2),
            )*/
    }
}