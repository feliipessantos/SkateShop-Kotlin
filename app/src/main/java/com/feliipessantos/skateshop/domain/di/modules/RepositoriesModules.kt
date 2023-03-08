package com.feliipessantos.skateshop.domain.di.modules

import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import com.feliipessantos.skateshop.data.repository.ViaCepRepository
import org.koin.dsl.module

val repositoriesModules = module {
    factory {
        AuthRepository()

    }

    factory {
        ProductsRepository()
    }

    factory {
        ViaCepRepository(get())
    }
}