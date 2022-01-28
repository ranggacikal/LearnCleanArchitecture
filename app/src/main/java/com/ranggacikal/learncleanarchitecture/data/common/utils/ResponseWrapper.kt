package com.ranggacikal.learncleanarchitecture.data.common.utils

import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    var code: Int,
    @SerializedName("message") var message : String,
    @SerializedName("status") var status : Boolean,
    @SerializedName("errors") var errors : List<String>? = null,
    @SerializedName("data") var data : List<T>? = null
)


data class WrappedResponse<T> (
    var code: Int,
    @SerializedName("pesan") var message : String,
    @SerializedName("sukses") var sukses : Boolean,
    @SerializedName("status") var status : Int,
    @SerializedName("data") var data : List<T>? = null
)