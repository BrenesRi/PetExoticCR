package com.example.petfinder.app.view.petlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petfinder.R
import com.example.petfinder.app.adapter.PetAdapter
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.PetItem
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.view.home.HomeDirections
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentHomeBinding
import com.example.petfinder.databinding.FragmentPetListBinding

class PetList : Fragment() {
    private lateinit var binding: FragmentPetListBinding
    private lateinit var petViewModel: PetViewModel

    // We need an adapter to connect with the recycle view
    private val adapter = PetAdapter { petItem -> onPetClicked(petItem) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Connect the recycler view with the adapter
        binding.rvPetList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPetList.adapter = adapter

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        // ViewModelFactory
        petViewModel =
            ViewModelProvider(this, ViewModelFactory(mainRepository))
                .get(PetViewModel::class.java)

        // Observer method to bind data of petList into Recycler View
        petViewModel.petList.observe(this) {
            adapter.setPetList(it)
        }
        petViewModel.findAllPet()
    }

    private fun onPetClicked(petItem: PetItem) {
        when (petItem) {
            is PetItem.PetType -> {
                val action = HomeDirections.actionPetListToPetDetail(petItem.pet.id)
                findNavController().navigate(action)
            }
            is PetItem.AdoptionRequestType -> {

            }
        }
    }
}