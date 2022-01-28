package com.ranggacikal.learncleanarchitecture.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id_user") var id_user: Int? = null,
    @SerializedName("nama_lengkap") var nama_lengkap: String? = null,
    @SerializedName("no_handphone") var no_handphone: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("kode_team") var kode_team: String? = null
)