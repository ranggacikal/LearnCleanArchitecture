package com.ranggacikal.learncleanarchitecture.domain.login

import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginRequest
import com.ranggacikal.learncleanarchitecture.data.login.remote.dto.LoginResponse
import com.ranggacikal.learncleanarchitecture.domain.login.entity.LoginEntity
import kotlinx.coroutines.flow.Flow
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult

interface LoginRepository {

    suspend fun login(loginRequest: LoginRequest) : Flow<BaseResult<LoginEntity,
            WrappedResponse<LoginResponse>>>

}