
package com.example.petfinder.app.view.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.petfinder.R
import androidx.navigation.fragment.findNavController

class DetailsFragmentSaved : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_detail_adoption_request, container, false)
        val button_accepted = view.findViewById<Button>(R.id.accepted_request_button)


        /*button_accepted.setOnClickListener {
            // Navega al destino del "home" de la aplicación utilizando la acción definida en el gráfico de navegación
            findNavController().navigate(R.id.action_homeScreen_to_detailsScreen)
        }*/

        return view
    }
}