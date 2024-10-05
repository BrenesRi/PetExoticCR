package com.example.petfinder.app.view.aipets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petfinder.R
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.Users
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentAiPetsBinding

class ListAI : Fragment() {
    private lateinit var binding: FragmentAiPetsBinding
    private lateinit var petViewModel: PetViewModel
    private var currentUser: Users? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAiPetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        // ViewModelFactory
        petViewModel =
            ViewModelProvider(this, ViewModelFactory(mainRepository))
                .get(PetViewModel::class.java)
        petViewModel.getUserByUsername(Profile.profileUsername)

        // Observer method to bind data of breedList into Spinner
        petViewModel.breedList.observe(viewLifecycleOwner) { breeds ->
            val filteredBreeds = breeds.filter { it.description != "N/A" }
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                filteredBreeds.map { it.description }
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.breedSpinner.adapter = adapter
        }

        petViewModel.findAllBreed()

        petViewModel.user.observe(viewLifecycleOwner) { user ->
            currentUser = user
        }

        petViewModel.chatGPTResponse.observe(viewLifecycleOwner) { response ->
            binding.iaEditText.setText(response.message)
        }

        val button = view.findViewById<Button>(R.id.iaButton)

        button.setOnClickListener {
            val breedDescription = binding.breedSpinner.selectedItem?.toString() ?: ""
            val provinceDescription = currentUser!!.province.description
            val base = "$breedDescription-$provinceDescription"
            petViewModel.getAiResponse(base)
        }
    }
}
