package com.example.petfinder.app.view.petlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petfinder.app.adapter.PetAdapter
import com.example.petfinder.app.model.PetItem
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.view.Saved.SavedFragmentDirections
import com.example.petfinder.app.view.adoptions.AdoptionsReqDirections
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentAdoptionRequestListBinding


class AdoptionList: Fragment() {
    private lateinit var binding: FragmentAdoptionRequestListBinding
    private lateinit var petViewModel: PetViewModel

    // We need an adapter to connect with the recycle view
    private val adapter = PetAdapter { petItem -> onPetClicked(petItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdoptionRequestListBinding.inflate(inflater, container, false)
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
        petViewModel.findMyAdoptionRequest()
    }
    private fun onPetClicked(petItem: PetItem) {
        when (petItem) {
            is PetItem.AdoptionRequestType -> {
                val action = AdoptionsReqDirections.actionAdoptionsReqToMyPetsDetails(petItem.adoptionRequest.id)
                findNavController().navigate(action)
            }
            is PetItem.PetType -> {
                // Handle this case if needed, or ignore it if this screen only deals with adoption requests
            }
        }
    }
}