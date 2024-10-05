package com.example.petfinder.app.view.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.petfinder.R
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.Users
import com.example.petfinder.app.repository.MainRepository
import com.example.petfinder.app.service.MainService
import com.example.petfinder.app.viewmodel.PetViewModel
import com.example.petfinder.app.viewmodel.ViewModelFactory
import com.example.petfinder.databinding.FragmentEditPetBinding
import com.example.petfinder.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding
    private lateinit var petViewModel: PetViewModel
    private var currentUser: Users? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainService = MainService.getInstance()
        val mainRepository = MainRepository(mainService)

        petViewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))
            .get(PetViewModel::class.java)
        petViewModel.getUserByUsername(Profile.profileUsername)

        petViewModel.user.observe(viewLifecycleOwner) { user ->
            currentUser = user
            binding.Title.text = getString(R.string.user_details)
            binding.usernameEditText.setText(currentUser!!.name)
            binding.IDEditText.setText("User ID: " + currentUser!!.id.toString())
            binding.emailInput.setText(currentUser!!.email)
            binding.phoneNumberEditText.setText(currentUser!!.phoneNumber)
            binding.locationEditText.setText(currentUser!!.province.description)
            if (Profile.profileValue == 0){
                binding.roleInput.text = "Role: Owner"
            } else{
                binding.roleInput.text = "Role: Adopter"
            }
        }
    }

}
