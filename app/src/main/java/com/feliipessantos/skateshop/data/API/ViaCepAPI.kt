package com.feliipessantos.skateshop.data.API

import com.feliipessantos.skateshop.domain.model.Address
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepAPI {
    @GET("ws/{cep}/json/")
    suspend fun setAddress(@Path("cep") cep: String): Response<Address>
}