package com.example.petfinder.app.repository

import com.example.petfinder.app.model.LoginRequest
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.model.UserLoginResponse
import com.example.petfinder.app.service.LoginService
import com.example.petfinder.app.utils.MyApplication.Companion.sessionManager
import retrofit2.Response
import android.util.Log

class LoginRepository constructor (
    private val loginService: LoginService
){

    // in-memory cache of the loggedInUser object
    private var user: UserLoginResponse? = null

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun getLoggedInUser(): UserLoginResponse? {
        return user
    }

    fun logout() {
        user = null
        sessionManager?.deleteAuthToken()
    }

    suspend fun login(userLogin: LoginRequest)  : Response<UserLoginResponse> {
        val response = loginService.login(userLogin)

        if (response.isSuccessful) {
            val loggedInUser = response.body()
            if (loggedInUser != null) {
                setLoggedInUser(loggedInUser, response.headers()["Authorization"].toString())
                checkUserRole(loggedInUser)
            }
        }

        return response
    }

    private fun setLoggedInUser(loginRequest: UserLoginResponse?, token:String) {
        this.user = loginRequest
        sessionManager?.saveAuthToken(token)
        Log.d("Token",token)

        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    private fun checkUserRole(user: UserLoginResponse) {
        user.authorities.forEach { authority ->
            when (authority.authority) {
                "ROLE_ADOPTER" -> {
                    Profile.profileValue = 1
                }
                "ROLE_OWNER" -> {
                    Profile.profileValue = 0
                }
            }
            Profile.profileUsername = user.username
        }
    }

}