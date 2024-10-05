package com.example.petfinder.app.view.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.petfinder.R
import com.example.petfinder.app.view.LoginActivity
import com.example.petfinder.app.viewmodel.LoginViewModel
import com.example.petfinder.app.viewmodel.LoginViewModelFactory

class ProfileWelcome : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_welcome, container, false)

        // Obtén una referencia al botón
        val signOutButton = view.findViewById<Button>(R.id.signOutButton)
        val detailButton = view.findViewById<Button>(R.id.detailButton)

        // Configura un OnClickListener para el botón
        signOutButton.setOnClickListener {
            // Crea un Intent para iniciar LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            loginViewModel =
                ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]
            loginViewModel.logout()
            startActivity(intent)
            activity?.finish()
        }

        detailButton.setOnClickListener {
            // Obtén una instancia del NavController
            val navController = findNavController()
            navController.navigate(R.id.action_profileScreen_to_sign_up_screen)
        }

        return view
    }
}
