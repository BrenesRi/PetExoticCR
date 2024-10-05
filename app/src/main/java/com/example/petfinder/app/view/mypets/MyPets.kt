package com.example.petfinder.app.view.mypets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.petfinder.R

class MyPets : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_pets, container, false)
        /*buttonAdoptionsView.setOnClickListener {
            // Navega al destino myPetsScreen utilizando la acción definida en el gráfico de navegación
            findNavController().navigate(R.id.action_myPetsScreen_to_adoptions2)
        }*/
        return view
    }
}