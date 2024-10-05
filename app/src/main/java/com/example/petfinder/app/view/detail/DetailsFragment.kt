package com.example.petfinder.app.view.detail

import android.os.Bundle
import com.example.petfinder.app.model.Profile
import androidx.lifecycle.Observer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.petfinder.R
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentDetailsBinding
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var petViewModel: PetViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(
            inflater,
            container,
            false
        ) // Asignar a la variable global
        binding.root.findViewById<Button>(R.id.likeButton).setOnClickListener {
            findNavController().navigate(R.id.action_detailsScreen_to_adoptionRequestDialogFragment)
        }
        return binding.root
    }

    private fun convertToBirthDate(birthDate: Any?): LocalDate {
        return when (birthDate) {
            is String -> {
                val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                LocalDate.parse(birthDate, formatter)
            }
            is Date -> birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            else -> throw IllegalArgumentException("Unsupported birthDate type")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        petViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))
            .get(PetViewModel::class.java)

        // Obtener el petId del argumento
        val petId = arguments?.getLong("petId") ?: -1

        val includedLayout = binding.includeLayoutDetails
        val includedImageLayout = binding.includeLayoutImageTitle

        if (Profile.profileValue == 0){
            includedLayout.likeButton.visibility = View.GONE //O INVISIBLE
        }

        petViewModel.pet.observe(this) {
            Profile.currentPetId = it.id
            val currentDate = LocalDate.now()
            val birthDate = convertToBirthDate(it.birthDate)
            val period = Period.between(birthDate, currentDate)

            includedImageLayout.name.text = it.name
            includedLayout.breed.text = it.breed.description
            includedLayout.gender.text = it.gender.description
            includedLayout.size.text = it.size.description
            includedLayout.type.text = it.type.description
            includedLayout.age.text = "${period.years} years"
            includedLayout.color.text = it.color
            includedLayout.province.text = it.user.province.description
            includedLayout.descriptionText.text = it.description
            includedLayout.specialCaresText.text = it.specialCare

            Glide.with(includedImageLayout.imageView)
                .load(it.imageName)
                .placeholder(R.drawable.goldendog)
                .error(R.drawable.smallcat)
                .into(includedImageLayout.imageView)
        }
        petViewModel.getPet(petId)
    }
}
