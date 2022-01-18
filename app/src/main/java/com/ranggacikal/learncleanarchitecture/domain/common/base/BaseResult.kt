package com.ranggacikal.learncleanarchitecture.domain.common.base

sealed class BaseResult<out T: Any, out U: Any> {

    data class Success<T: Any>(val data: T) : BaseResult<T, Nothing>()
    data class Error<U: Any>(val rawReponse: U): BaseResult<Nothing, U>()

}