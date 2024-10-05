package com.example.petfinder.app.view.petlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petfinder.app.adapter.PetAdapter
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.PetItem
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.view.Saved.SavedFragmentDirections
import com.example.petfinder.app.view.home.HomeDirections
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentPetListSavedBinding

class PetListSaved : Fragment() {
    private lateinit var binding: FragmentPetListSavedBinding
    private lateinit var petViewModel: PetViewModel

    // We need an adapter to connect with the recycle view
    private val adapter = PetAdapter { petItem -> onPetClicked(petItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetListSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect the recycler view with the adapter
        binding.rvPetListSaved.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPetListSaved.adapter = adapter

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        // ViewModelFactory
        petViewModel =
            ViewModelProvider(this, ViewModelFactory(mainRepository))
                .get(PetViewModel::class.java)

        // Observer method to bind data of petList into Recycler View
        petViewModel.adoptionRequestList.observe(viewLifecycleOwner) { adoptionRequests ->
            adapter.setAdoptionRequestList(adoptionRequests)
        }
        petViewModel.findSavedAdoptionRequest()
    }
    private fun onPetClicked(petItem: PetItem) {
        when (petItem) {
            is PetItem.AdoptionRequestType -> {
                val action = SavedFragmentDirections.actionSavedScreenToSavedDetails(petItem.adoptionRequest.id)
                findNavController().navigate(action)
            }
            is PetItem.PetType -> {
                // Handle this case if needed, or ignore it if this screen only deals with adoption requests
            }
        }
    }
}