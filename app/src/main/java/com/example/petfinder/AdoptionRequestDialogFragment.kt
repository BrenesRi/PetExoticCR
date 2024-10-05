package com.example.petfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petfinder.app.model.AdoptionRequest
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.Users
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentAdoptionRequestDialogBinding
import com.example.petfinder.databinding.FragmentEditPetBinding

class AdoptionRequestDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentAdoptionRequestDialogBinding
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
        binding = FragmentAdoptionRequestDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonOk= view.findViewById<Button>(R.id.adoptButton)
        val buttonCancel = view.findViewById<Button>(R.id.GoBackButton)

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        petViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))
            .get(PetViewModel::class.java)
        petViewModel.getUserByUsername(Profile.profileUsername)

        petViewModel.user.observe(viewLifecycleOwner) { user ->
            currentUser = user
        }

        val petId = Profile.currentPetId

        petViewModel.pet.observe(this) {
            currentPet = it
        }
        if (petId != null) {
            petViewModel.getPet(petId)
        }

        buttonOk.setOnClickListener {
            val description =
                binding.descriptionEditText.text.toString() ?: "Im interested in this Pet!"
            val newAdoptionRequest = AdoptionRequest(
                pet = currentPet!!,
                user = currentUser!!,
                adoptionMessage = description,
                isAdopted = false
            )
            petViewModel.createAdoptionRequest(newAdoptionRequest)
            Profile.currentPetId = null
            findNavController().navigate(R.id.action_adoptionRequestDialogFragment_to_detailsScreen)
        }

        buttonCancel.setOnClickListener {
            findNavController().navigate(R.id.action_adoptionRequestDialogFragment_to_detailsScreen)
        }
    }





}