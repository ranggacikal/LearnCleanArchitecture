package com.ranggacikal.learncleanarchitecture.domain.login.entity

data class LoginEntity(
    var id_user : Int,
    var nama_lengkap: String,
    var no_handphone: String,
    var email : String,
    var password: String,
    var kode_team: String
)