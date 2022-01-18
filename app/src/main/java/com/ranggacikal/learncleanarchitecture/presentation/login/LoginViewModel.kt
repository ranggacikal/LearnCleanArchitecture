package com.ranggacikal.learncleanarchitecture.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult
import com.ranggacikal.learncleanarchitecture.domain.login.entity.LoginEntity
import com.ranggacikal.learncleanarchitecture.domain.login.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val state = MutableStateFlow<LoginActivityState>(LoginActivityState.Init)
    val mState : StateFlow<LoginActivityState> get() = state

    private fun setLoading(){
        state.value = LoginActivityState.isLoading(true)
    }

    private fun hideLoading(){
        state.value = LoginActivityState.isLoading(false)
    }

    private fun showToast(message: String){
        state.value = LoginActivityState.showToast(message)
    }

    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            loginUseCase.execute(loginRequest)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { baseResult ->
                    hideLoading()
                    Log.d("cekRawResponse", "login: ${baseResult.toString()}")
                    when(baseResult){
                        is BaseResult.Error -> state.value = LoginActivityState
                            .ErrorLogin(baseResult.rawReponse)
                        is BaseResult.Success -> state.value = LoginActivityState
                            .SuccessLogin(baseResult.data)
                    }
                }
        }
    }

}

sealed class LoginActivityState{
    object Init: LoginActivityState()
    data class isLoading(val isLoading: Boolean) : LoginActivityState()
    data class showToast(val message: String) : LoginActivityState()
    data class SuccessLogin(val loginEntity: LoginEntity) : LoginActivityState()
    data class ErrorLogin(val rawResponse: WrappedResponse<LoginResponse>) : LoginActivityState()
}