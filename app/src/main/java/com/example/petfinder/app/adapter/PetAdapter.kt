package com.example.petfinder.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petfinder.R
import com.example.petfinder.app.model.AdoptionRequest
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.PetItem
import com.example.petfinder.databinding.PetItemBinding
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


class PetAdapter(private val clickListener: (PetItem) -> Unit) : RecyclerView.Adapter<MainViewHolder>() {

    private var petItems: List<PetItem> = listOf()

    fun setPetList(pets: List<Pet>) {
        petItems = pets.map { PetItem.PetType(it) }
        notifyDataSetChanged()
    }

    fun setAdoptionRequestList(adoptionRequests: List<AdoptionRequest>) {
        petItems = adoptionRequests.map { PetItem.AdoptionRequestType(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PetItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        when (val item = petItems[position]) {
            is PetItem.PetType -> bindPet(holder, item.pet)
            is PetItem.AdoptionRequestType -> bindAdoptionRequest(holder, item.adoptionRequest)
        }
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
    private fun bindPet(holder: MainViewHolder, pet: Pet) {

        val currentDate = LocalDate.now()
        val birthDate = convertToBirthDate(pet.birthDate)
        val period = Period.between(birthDate, currentDate)

        holder.binding.petName.text = pet.name
        holder.binding.age.text = "${period.years} years"
        holder.binding.gender.text = pet.gender.description
        holder.binding.breed.text = pet.breed.description
        holder.binding.extra.text = pet.user.province.description

        val errorDrawable: Int =
            if ((pet.id % 2).toInt() == 0) R.drawable.smallcat else R.drawable.smallcat

        Glide.with(holder.binding.imageView)
            .load(pet.imageName)
            .placeholder(R.drawable.smallcat)
            .error(errorDrawable)
            .into(holder.binding.imageView)

        holder.binding.root.setOnClickListener {
            clickListener(PetItem.PetType(pet))
        }
    }

    private fun bindAdoptionRequest(holder: MainViewHolder, adoptionRequest: AdoptionRequest) {
        val pet = adoptionRequest.pet
        // Calculating age
        val currentDate = LocalDate.now()
        val birthDate = convertToBirthDate(pet.birthDate)
        val period = Period.between(birthDate, currentDate)

        holder.binding.petName.text = pet.name
        holder.binding.age.text = "${period.years} years"
        holder.binding.gender.text = pet.gender.description
        holder.binding.breed.text = pet.breed.description
        holder.binding.extra.text = adoptionRequest.user.province.description

        val errorDrawable: Int =
            if ((pet.id % 2).toInt() == 0) R.drawable.smallcat else R.drawable.smallcat

        Glide.with(holder.binding.imageView)
            .load(pet.imageName)
            .placeholder(R.drawable.smallcat)
            .error(errorDrawable)
            .into(holder.binding.imageView)

        holder.binding.root.setOnClickListener {
            clickListener(PetItem.AdoptionRequestType(adoptionRequest))
        }
    }

    override fun getItemCount(): Int = petItems.size
}

class MainViewHolder(
    val binding: PetItemBinding
) : RecyclerView.ViewHolder(binding.root)
