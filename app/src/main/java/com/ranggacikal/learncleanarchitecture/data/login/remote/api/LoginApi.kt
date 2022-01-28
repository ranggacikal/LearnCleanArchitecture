package com.ranggacikal.learncleanarchitecture.data.login.remote.api

import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<WrappedResponse<LoginResponse>>

}