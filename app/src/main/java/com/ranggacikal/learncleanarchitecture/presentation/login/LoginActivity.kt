package com.ranggacikal.learncleanarchitecture.presentation.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ranggacikal.learncleanarchitecture.R
import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import com.ranggacikal.learncleanarchitecture.databinding.ActivityLoginBinding
import com.ranggacikal.learncleanarchitecture.domain.login.entity.LoginEntity
import com.ranggacikal.learncleanarchitecture.infra.utils.SharedPrefs
import com.ranggacikal.learncleanarchitecture.presentation.common.extension.isEmail
import com.ranggacikal.learncleanarchitecture.presentation.common.extension.showGenericAlertDialog
import com.ranggacikal.learncleanarchitecture.presentation.common.extension.showToast
import com.ranggacikal.learncleanarchitecture.presentation.main.MainActivity
import com.ranggacikal.learncleanarchitecture.presentation.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        login()
        observe()
    }

    private fun observe() {
        viewModel.mState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleStateChange(state) }
            .launchIn(lifecycleScope)
    }

    private fun handleStateChange(state: LoginActivityState) {
        when (state){
            is LoginActivityState.Init -> Unit
            is LoginActivityState.ErrorLogin -> handleErrorLogin(state.rawResponse)
            is LoginActivityState.SuccessLogin -> handleSuccessLogin(state.loginEntity)
            is LoginActivityState.showToast -> showToast(state.message)
            is LoginActivityState.isLoading -> handleLoading(state.isLoading)
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.loginButton.isEnabled = !loading
        binding.registerButton.isCheckable = !loading
        binding.loadingProgressBar.isIndeterminate = loading
        if (!loading){
            binding.loadingProgressBar.progress = 0
        }
    }

    private fun handleSuccessLogin(loginEntity: LoginEntity) {

        showToast("Welcome ${loginEntity.nama_lengkap}")
        goToMainActivity()

    }

    private fun goToMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    private fun handleErrorLogin(response: WrappedResponse<LoginResponse>) {
        Log.d("cekHandleError", "login: ${response.message}")
        showGenericAlertDialog(response.message)
    }

    private fun login() {

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (validate(email, password)){
                val loginRequest = LoginRequest(email, password)
                viewModel.login(loginRequest)
            }
        }

    }

    private fun validate(email: String, password: String) : Boolean{
        resetAllInputError()
        if (!email.isEmail()){
            setEmailError(Constants.ERROR_EMAIL_NOT_VALID)
            return false
        }

        if (password.length < 8){
            setPasswordError(Constants.ERROR_PASSWORD_NOT_VALID)
            return false
        }

        return true
    }

    private fun resetAllInputError() {
        setEmailError(null)
        setPasswordError(null)
    }

    private fun setEmailError(e: String?){
        binding.emailInput.error = e
    }

    private fun setPasswordError(e: String?){
        binding.passwordInput.error = e
    }
}