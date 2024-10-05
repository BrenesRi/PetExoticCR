package com.example.petfinder.app.view.add

import com.example.petfinder.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petfinder.app.model.Breed
import com.example.petfinder.app.model.Gender
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.Size
import com.example.petfinder.app.model.Type
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentAddBinding
import java.util.Calendar
import java.util.Locale


class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var petViewModel: PetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)
        petViewModel =
            ViewModelProvider(this, ViewModelFactory(mainRepository))
                .get(PetViewModel::class.java)

        petViewModel.findAllBreed()
        petViewModel.getUserByUsername(Profile.profileUsername)

        petViewModel.breedList.observe(viewLifecycleOwner) { breeds ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                breeds.map { it.description }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.includeSearchBarDogCat.spinnerBreedAdd.adapter = adapter
        }
        petViewModel.user.observe(viewLifecycleOwner) { user ->
            user?.let { newUser ->
                binding.buttonAdd.setOnClickListener {
                    val sizes = listOf(
                        Size(id = 1, description = "Small"),
                        Size(id = 2, description = "Medium"),
                        Size(id = 3, description = "Large")
                    )
                    val types = listOf(
                        Type(id = 1, description = "Dog"),
                        Type(id = 2, description = "Cat")
                    )
                    val genders = listOf(
                        Gender(id = 1, description = "Male"),
                        Gender(id = 2, description = "Female")
                    )

                    val breedDescription =
                        binding.includeSearchBarDogCat.spinnerBreedAdd.selectedItem?.toString()
                            ?: ""

                    val breeds = petViewModel.breedList.value ?: emptyList()
                    petViewModel.findAllPet()
                    val petList = petViewModel.petList.value ?: emptyList()

                    val lastPetId = if (petList.isNotEmpty()) {
                        val lastPet = petList.last()
                        lastPet.id
                    } else {
                        -1
                    }

                    val selectedBreed = breeds.find { it.description == breedDescription }

                    val name = binding.includeSearchBarDogCat.nameEditText.text?.toString() ?: ""
                    val description =
                        binding.includeSearchBarDogCat.descriptionEditText.text?.toString() ?: ""
                    val color = binding.includeSearchBarDogCat.colorEditText.text?.toString() ?: ""
                    val specialCares =
                        binding.includeSearchBarDogCat.specialCaresEditText.text?.toString() ?: ""
                    val typeDescription =
                        if (binding.includeSearchBarDogCat.radioButton.isChecked) "Dog" else "Cat"
                    val genderDescription =
                        if (binding.includeSearchBarDogCat.radioButton3.isChecked) "Male" else "Female"
                    val sizeDescription =
                        binding.includeSearchBarDogCat.spinnerSizes.selectedItem.toString()

                    val type = types.find { it.description == typeDescription }
                    val gender = genders.find { it.description == genderDescription }
                    val size = sizes.find { it.description == sizeDescription }
                    val defaultBreed = Breed(id = 0, description = "Default")
                    val breed = selectedBreed ?: defaultBreed
                    val ageYears =
                        binding.includeSearchBarDogCat.spinnerAges.selectedItem.toString().toInt()
                    if (type != null && gender != null && size != null) {
                        val birthDate = getFormattedBirthDateFromAge(ageYears)

                        val newPet = Pet(
                            description = description,
                            name = name,
                            gender = gender,
                            birthDate = birthDate,
                            size = size,
                            imageName = "",
                            color = color,
                            user = newUser,
                            type = type,
                            specialCare = specialCares,
                            breed = breed,
                        )
                        petViewModel.createPet(newPet)
                        findNavController().navigate(R.id.addScreen_to_myPetsScreen)
                    }
                }
            }
        }
    }
    private fun getFormattedBirthDateFromAge(ageYears: Int): String {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val petYear = currentYear - ageYears
        return String.format(
            Locale.getDefault(),
            "%04d-%02d-%02d",
            petYear,
            currentMonth,
            currentDay
        )
    }
}