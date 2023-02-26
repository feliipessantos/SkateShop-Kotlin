package com.example.skateshop.API

import com.example.skateshop.model.Address
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ZipCodeAPI {
    @GET("ws/{cep}/json/")
    fun setAddress(@Path("cep") cep: String): Call<Address>
}