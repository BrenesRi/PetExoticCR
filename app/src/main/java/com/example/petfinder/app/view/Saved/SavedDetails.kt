package com.example.petfinder.app.view.Saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.petfinder.R
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentDetailsBinding
import com.example.petfinder.databinding.FragmentSavedDetailsBinding
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

class savedDetails : Fragment() {

    private lateinit var binding: FragmentSavedDetailsBinding
    private lateinit var petViewModel: PetViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentSavedDetailsBinding.inflate(
        inflater,
        container,
        false
    )
        // Inflate the layout for this fragment  cancel_request_button

        val view = inflater.inflate(R.layout.fragment_saved_details, container, false)
        val button = view.findViewById<Button>(R.id.accepted_request_button)
        val button_cancel = view.findViewById<Button>(R.id.cancel_request_button)
        button.setOnClickListener {
            // Navega al destino del "home" de la aplicación utilizando la acción definida en el gráfico de navegación
            findNavController().navigate(R.id.action_savedDetails_to_savedScreen2)
        }
        button_cancel.setOnClickListener {
            // Navega al destino del "home" de la aplicación utilizando la acción definida en el gráfico de navegación
            findNavController().navigate(R.id.action_savedDetails_to_savedScreen2)
        }
        return binding.root


        //return view
        //return inflater.inflate(R.layout.fragment_saved_details, container, false)
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
        val adoptionId = arguments?.getLong("petId") ?: -1

        val includedLayout = binding.includeLayoutDetailsAdoptionRequest
        val includedImageLayout = binding.includeLayoutImageTitle

        petViewModel.adoptionRequest.observe(this) {

            val currentDate = LocalDate.now()
            val birthDate = convertToBirthDate(it.pet.birthDate)
            val period = Period.between(birthDate, currentDate)

            includedImageLayout.name.text = it.pet.name
            includedLayout.adoptionRequestDescriptionInput.setText(it.adoptionMessage)
            includedLayout.breed.text = it.pet.breed.description
            includedLayout.gender.text = it.pet.gender.description
            includedLayout.type.text = it.pet.type.description
            includedLayout.age.text = "${period.years} years"
            includedLayout.size.text = it.pet.size.description
            includedLayout.color.text = it.pet.color
            includedLayout.province.text = it.pet.user.province.description
            includedLayout.descriptionText.text = it.pet.description
            includedLayout.specialCaresText.text = it.pet.specialCare

            Glide.with(includedImageLayout.imageView)
                .load(it.pet.imageName)
                .placeholder(R.drawable.goldendog)
                .error(R.drawable.smallcat)
                .into(includedImageLayout.imageView)
        }
        petViewModel.getAdoptionRequest(adoptionId)
    }

}
