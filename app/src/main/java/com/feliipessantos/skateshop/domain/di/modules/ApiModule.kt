package com.feliipessantos.skateshop.domain.di.modules

import com.feliipessantos.skateshop.data.API.ViaCepAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { viaCepApi() }
}

fun viaCepApi(): ViaCepAPI {
    return Retrofit.Builder()
        .baseUrl("https://viacep.com.br/ws/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ViaCepAPI::class.java)
}
