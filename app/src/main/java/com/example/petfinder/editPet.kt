package com.example.petfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petfinder.app.model.Breed
import com.example.petfinder.app.model.Gender
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.Size
import com.example.petfinder.app.model.Type
import com.example.petfinder.app.model.Users
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentEditPetBinding
import java.util.Calendar
import java.util.Locale

class editPet : Fragment() {
    private lateinit var binding: FragmentEditPetBinding
    private lateinit var petViewModel: PetViewModel
    private var currentUser: Users? = null
    private var currentPet: Pet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditPetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        // Inicializa petViewModel
        petViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))
            .get(PetViewModel::class.java)
        petViewModel.getUserByUsername(Profile.profileUsername)

        // Observa el usuario actual y almacénalo en currentUser
        petViewModel.user.observe(viewLifecycleOwner) { user ->
            currentUser = user
        }

        val buttonSave = view.findViewById<Button>(R.id.buttonSave)
        val buttonDelete = view.findViewById<Button>(R.id.buttonDelete)

        petViewModel.findAllBreed()
        val petId = arguments?.getLong("petId") ?: -1

        val includedLayout = binding.includeLayoutAddForm
        petViewModel.breedList.observe(viewLifecycleOwner) { breeds ->
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                breeds.map { it.description } // Assuming Breed has a 'name' property
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            includedLayout.spinnerBreedAdd.adapter = adapter
        }

        petViewModel.pet.observe(this) {
            currentPet = it
            includedLayout.nameEditText.setText(it.name)
            includedLayout.descriptionEditText.setText(it.description)
            includedLayout.colorEditText.setText(it.color)
            includedLayout.specialCaresEditText.setText(it.specialCare)

            includedLayout.radioButton.visibility = View.GONE
            includedLayout.radioButton2.visibility = View.GONE
            includedLayout.radioGroup.visibility = View.GONE
            includedLayout.radioButton3.visibility = View.GONE
            includedLayout.radioButton4.visibility = View.GONE
            includedLayout.radioGroup2.visibility = View.GONE
            includedLayout.spinnerSizes.visibility = View.GONE
            includedLayout.spinnerAges.visibility = View.GONE
            includedLayout.spinnerBreedAdd.visibility = View.GONE
            includedLayout.breedTextView.visibility = View.GONE
            includedLayout.sizeTextView.visibility = View.GONE
            includedLayout.ageTextView.visibility = View.GONE
            includedLayout.typeTextView.visibility = View.GONE
            includedLayout.sexTextView.visibility = View.GONE

            Glide.with(binding.editImageView)
                .load(it.imageName)
                .placeholder(R.drawable.goldendog)
                .error(R.drawable.smallcat)
                .into(binding.editImageView)
        }
        petViewModel.getPet(petId)

        //Listener for update
        buttonSave.setOnClickListener {
            val newUser = currentUser
            if (newUser != null) {
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
                    binding.includeLayoutAddForm.spinnerBreedAdd.selectedItem?.toString() ?: ""
                val breeds = petViewModel.breedList.value ?: emptyList()
                petViewModel.findAllPet()
                val selectedBreed = breeds.find { it.description == breedDescription }

                val name = binding.includeLayoutAddForm.nameEditText.text?.toString() ?: ""
                val description =
                    binding.includeLayoutAddForm.descriptionEditText.text?.toString() ?: ""
                val color = binding.includeLayoutAddForm.colorEditText.text?.toString() ?: ""
                val specialCares =
                    binding.includeLayoutAddForm.specialCaresEditText.text?.toString() ?: ""
                val typeDescription =
                    if (binding.includeLayoutAddForm.radioButton.isChecked) "Dog" else "Cat"
                val genderDescription =
                    if (binding.includeLayoutAddForm.radioButton3.isChecked) "Male" else "Female"
                val sizeDescription =
                    binding.includeLayoutAddForm.spinnerSizes.selectedItem.toString()

                val type = types.find { it.description == typeDescription }
                val gender = genders.find { it.description == genderDescription }
                val size = sizes.find { it.description == sizeDescription }
                val defaultBreed = Breed(id = 0, description = "Default")
                val breed = selectedBreed ?: defaultBreed
                val ageYears =
                    binding.includeLayoutAddForm.spinnerAges.selectedItem.toString().toInt()

                fun getFormattedBirthDateFromAge(ageYears: Int): String {
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

                if (type != null && gender != null && size != null) {
                    val birthDate = getFormattedBirthDateFromAge(ageYears)

                    val newPet = Pet(
                        id = petId,
                        description = description,
                        name = name,
                        birthDate = currentPet!!.birthDate,
                        gender = currentPet!!.gender,
                        size = currentPet!!.size,
                        imageName = "",
                        color = color,
                        user = newUser, // Aquí se asigna el usuario logueado
                        type = currentPet!!.type,
                        specialCare = specialCares,
                        breed = currentPet!!.breed,
                    )
                    // Método para actualizar la mascota
                    petViewModel.updatePet(newPet)
                    findNavController().navigate(R.id.action_editPet_to_myPetsScreen)
                }
            }
        }

        //Listener for Delete
        buttonDelete.setOnClickListener {
            petViewModel.deletePet(petId)
            findNavController().navigate(R.id.action_editPet_to_myPetsScreen)
        }
    }
}
