package com.ranggacikal.learncleanarchitecture.data.login.repository

import android.util.Log
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
            if (response.isSuccessful) {
                Log.d("cekStatus", "isSukses: true")
                val body = response.body()!!
                if (body.status == 1) {
                    Log.d("cekStatus", "login: SUKSES")
                    Log.d("cekStatus", "body.sukses: ${body.sukses}")
                    Log.d("cekStatus", "body.status: ${body.status}")
                    val loginEntity = LoginEntity(
                        body.data!![0].id_user!!, body.data!![0].nama_lengkap!!,
                        body.data!![0].no_handphone!!, body.data!![0].email!!,
                        body.data!![0].password!!, body.data!![0].kode_team!!
                    )
                    emit(BaseResult.Success(loginEntity))
                } else {
                    Log.d("cekStatus", "login: GAGAL")
                    Log.d("cekStatus", "body.sukses: ${body.sukses}")
                    Log.d("cekStatus", "body.status: ${body.status}")
                    Log.d("cekStatus", "body.pesan: ${body.message}")
                    val type = object : TypeToken<WrappedResponse<LoginResponse>>() {}.type
                    val err: WrappedResponse<LoginResponse> = Gson().fromJson(
                        response.errorBody()!!
                            .charStream(), type
                    )
                    Log.d("cekStatus", "body.error: $err")
                    Log.d("cekStatus", "body.error: $err")
                    err.code = response.code()
                    emit(BaseResult.Error(err))
                }
            }else{
                Log.d("cekStatus", "isSukses: False")
            }
//            else{
//                val type = object : TypeToken<WrappedResponse<LoginResponse>>(){}.type
//                val err : WrappedResponse<LoginResponse> = Gson().fromJson(response.errorBody()!!
//                    .charStream(), type)
//                err.code = response.code()
//                emit(BaseResult.Error(err))
//            }
        }
    }
}