package com.ranggacikal.learncleanarchitecture.domain.register.usecase

import com.ranggacikal.learncleanarchitecture.data.common.utils.WrappedResponse
import com.ranggacikal.learncleanarchitecture.data.register.remote.dto.RegisterRequest
import com.ranggacikal.learncleanarchitecture.data.register.remote.dto.RegisterResponse
import com.ranggacikal.learncleanarchitecture.domain.common.base.BaseResult
import com.ranggacikal.learncleanarchitecture.domain.register.RegisterRepository
import com.ranggacikal.learncleanarchitecture.domain.register.entity.RegisterEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val registerRepository: RegisterRepository) {
    suspend fun invoke(registerRequest: RegisterRequest) : Flow<BaseResult<RegisterEntity,
            WrappedResponse<RegisterResponse>>>{
        return registerRepository.register(registerRequest)
    }
}