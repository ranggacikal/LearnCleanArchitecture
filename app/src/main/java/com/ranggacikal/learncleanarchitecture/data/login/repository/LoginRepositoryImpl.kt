package com.ranggacikal.learncleanarchitecture.data.login.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.api.LoginApi
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult
import com.ranggacikal.learncleanarchitecture.domain.login.LoginRepository
import com.ranggacikal.learncleanarchitecture.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val loginApi: LoginApi) : LoginRepository {
    override suspend fun login(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity,
            WrappedResponse<LoginResponse>>> {
        return flow {
            val response = loginApi.login(loginRequest)
            if(response.isSuccessful){
                val body = response.body()!!
                val loginEntity = LoginEntity(body.data?.id!!, body.data?.name!!,
                    body.data?.email!!, body.data?.token!!)
                emit(BaseResult.Success(loginEntity))
            }else{
                val type = object : TypeToken<WrappedResponse<LoginResponse>>(){}.type
                val err : WrappedResponse<LoginResponse> = Gson().fromJson(response.errorBody()!!
                    .charStream(), type)
                err.code = response.code()
                emit(BaseResult.Error(err))
            }
        }
    }
}