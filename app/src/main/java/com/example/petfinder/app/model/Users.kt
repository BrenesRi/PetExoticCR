package com.example.petfinder.app.model
//TODO: Privilege, Role y User al final
data class Users(
    val id: Long = 0L,
    val name: String = "",
    val phoneNumber: String = "",
    val enabled: Boolean = false, // Obligatorio con valor predeterminado
    val tokenExpired: Boolean = false, // Obligatorio con valor predeterminado
    val email: String = "",
    val role: Role = Role(), // Relación con valores predeterminados
    val province: Province = Province() // Relación con valores predeterminados
)


/**
 * Data validation state of the login form.
 */
data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
    val success: LoggedInUserView? = null,
    val error: Int? = null
)

data class LoginRequest(
    var username: String,
    var password: String,
)

/**
 * User details post authentication that is exposed to the UI
 */
data class LoggedInUserView(
    val username: String,
    //... other data fields that may be accessible to the UI
)

data class UserLoginResponse(
    var username: String,
    var password: String,
    var authorities: List<Authority>,
    var accountNonExpired: Boolean,
    var accountNonLocked: Boolean,
    var credentialsNonExpired: Boolean,
    var enabled: Boolean,
)

data class Authority(
    var authority: String,
)