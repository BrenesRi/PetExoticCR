package com.example.petfinder.app.view.adoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.petfinder.R

class AdoptionsReq: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_adoption_request, container, false)
        /*buttonAdoptionsView.setOnClickListener {
            // Navega al destino myPetsScreen utilizando la acción definida en el gráfico de navegación
            findNavController().navigate(R.id.action_myPetsScreen_to_adoptions2)
        }*/
        return view
    }
}