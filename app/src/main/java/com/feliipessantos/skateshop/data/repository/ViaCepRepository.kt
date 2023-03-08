package com.feliipessantos.skateshop.data.repository

import com.feliipessantos.skateshop.data.API.ViaCepAPI

class ViaCepRepository(private val api: ViaCepAPI) {

    suspend fun getCep(cep: String) = api.setAddress(cep)

}