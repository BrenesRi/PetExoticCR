package com.example.petfinder.app.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.petfinder.app.model.AdoptionRequest
import com.example.petfinder.app.model.Breed
import com.example.petfinder.app.model.Gender
import com.example.petfinder.app.model.Message
import com.example.petfinder.app.model.Pet
import com.example.petfinder.app.model.PetProvider
import com.example.petfinder.app.model.Size
import com.example.petfinder.app.model.Type
import com.example.petfinder.app.model.Users
import com.example.petfinder.app.repository.MainRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.IDN

class PetViewModel constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    val pet =  MutableLiveData<Pet>()
    val adoptionRequest = MutableLiveData<AdoptionRequest>()
    val adoptionRequestList = MutableLiveData<List<AdoptionRequest>>()
    val petList = MutableLiveData<List<Pet>>()
    val breedList =  MutableLiveData<List<Breed>>()
    val genderList =  MutableLiveData<List<Gender>>()
    val user = MutableLiveData<Users>()
    val sizeList =  MutableLiveData<List<Size>>()
    val typeList =  MutableLiveData<List<Type>>()
    val errorMessage = MutableLiveData<String>()
    val chatGPTResponse = MutableLiveData<Message>()
    val loading = MutableLiveData<Boolean>()
    var job: Job? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getPet(id : Long) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getPetById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    pet.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun deletePet(id : Long) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.deletePetById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    findMyPet()
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun getUserByUsername(email : String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getUserByEmail(email)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun findAllPet() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllPets()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    petList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun findMyPet() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getMyPets()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    petList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun updatePet(pet: Pet) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.updatePet(pet)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    fun createPet(pet: Pet) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.postPet(pet)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    fun createAdoptionRequest(adoptionRequest: AdoptionRequest) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.postAdoptionRequest(adoptionRequest)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    loading.value = false
                } else {
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    fun findSavedAdoptionRequest() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getSavedAdoptionRequests()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    adoptionRequestList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun findMyAdoptionRequest() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getMyAdoptionRequests()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    adoptionRequestList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun getAdoptionRequest(id : Long) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAdoptionRequestById(id)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    adoptionRequest.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
        println(adoptionRequest)
    }


    fun findAllBreed() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllBreeds()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    breedList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    fun findAllGenders() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllGenders()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    genderList.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error fetching genders: ${response.message()}")
                }
            }
        }
    }

    fun findAllSizes() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllSizes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    sizeList.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error fetching sizes: ${response.message()}")
                }
            }
        }
    }

    fun findAllTypes() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.getAllTypes()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    typeList.postValue(response.body())
                    loading.postValue(false)
                } else {
                    onError("Error fetching types: ${response.message()}")
                }
            }
        }
    }

    fun getAiResponse(base : String) {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = mainRepository.aiRequestMessage(base)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    chatGPTResponse.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
        loading.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}