package com.ranggacikal.learncleanarchitecture.domain.login.usecase

import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult
import com.ranggacikal.learncleanarchitecture.domain.login.LoginRepository
import com.ranggacikal.learncleanarchitecture.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {

    suspend fun execute(loginRequest: LoginRequest): Flow<BaseResult<LoginEntity,
            WrappedResponse<LoginResponse>>>{
        return loginRepository.login(loginRequest)
    }

}