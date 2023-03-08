package com.feliipessantos.skateshop.domain.di

import android.app.Application
import com.feliipessantos.skateshop.domain.di.modules.apiModule
import com.feliipessantos.skateshop.domain.di.modules.repositoriesModules
import com.feliipessantos.skateshop.domain.di.modules.viewModelsModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyApp)
            modules(repositoriesModules, viewModelsModules, apiModule)
        }
    }

}