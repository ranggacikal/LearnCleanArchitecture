package com.ranggacikal.learncleanarchitecture.domain.register

import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.register.remote.dto.RegisterRequest
import com.ranggacikal.learncleanarchitecture.data.register.remote.dto.RegisterResponse
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult
import com.ranggacikal.learncleanarchitecture.domain.register.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun register(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity,
            WrappedResponse<RegisterResponse>>>
}