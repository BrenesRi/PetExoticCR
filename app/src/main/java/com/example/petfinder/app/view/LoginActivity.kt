package com.example.petfinder.app.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.petfinder.R
import com.example.petfinder.app.model.LoggedInUserView
import com.example.petfinder.app.model.LoginRequest
import com.example.petfinder.app.view.signup.SignUpFragment
import com.example.petfinder.databinding.ActivityLoginBinding
import com.example.petfinder.app.model.Profile
import com.example.petfinder.app.utils.SessionManager
import com.example.petfinder.app.viewmodel.LoginViewModel
import com.example.petfinder.app.viewmodel.LoginViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // With View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        // LoginViewModelFactory
        loginViewModel =
            ViewModelProvider(this, LoginViewModelFactory())[LoginViewModel::class.java]

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            binding.loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                binding.usernameEditText.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                binding.passwordEditText.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResponse.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            binding.loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        binding.usernameEditText.afterTextChanged {
            loginViewModel.loginDataChanged(
                LoginRequest(
                    username = binding.usernameEditText.text.toString(),
                    password = binding.passwordEditText.text.toString()
                )
            )
        }

        binding.passwordEditText.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    LoginRequest(
                        username = binding.usernameEditText.text.toString(),
                        password = binding.passwordEditText.text.toString()
                    )
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            LoginRequest(
                                username = binding.usernameEditText.text.toString(),
                                password = binding.passwordEditText.text.toString()
                            )
                        )
                }
                false
            }

            binding.loginButton.setOnClickListener {
                binding.loading.visibility = View.VISIBLE
                loginViewModel.login(
                    LoginRequest(
                        username = binding.usernameEditText.text.toString(),
                        password = binding.passwordEditText.text.toString()
                    )
                )
            }
        }

        /*binding.signUpButton.setOnClickListener {
            // Crear una nueva instancia del fragmento SignUpFragment
            //val signUpFragment = SignUpFragment()
            //Log.d("SignUpFragment", "SignUpFragment instance created successfully")

            // Reemplazar el fragmento actual con SignUpFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, signUpFragment)
                .addToBackStack(null)
                .commit()
        }*/

    }

    /**
     * Success login to redirect the app to the next Screen
     */
    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val username = model.username

        // Initiate successful logged in experience
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        Toast.makeText(
            applicationContext,
            "$welcome $username",
            Toast.LENGTH_LONG
        ).show()
    }

    /**
     * Unsuccessful login
     */
    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}