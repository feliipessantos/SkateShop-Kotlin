package com.feliipessantos.skateshop.domain.di.modules

import com.feliipessantos.skateshop.ui.views.cart.CartViewModel
import com.feliipessantos.skateshop.ui.views.checkout.CheckoutViewModel
import com.feliipessantos.skateshop.ui.views.detailsProduct.DetailsProductViewModel
import com.feliipessantos.skateshop.ui.views.login.LoginViewModel
import com.feliipessantos.skateshop.ui.views.products.ProductViewModel
import com.feliipessantos.skateshop.ui.views.signUp.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModules = module {
    viewModel {
        LoginViewModel(get())
    }

    viewModel {
        SignUpViewModel(get())
    }

    viewModel {
        ProductViewModel(get())
    }

    viewModel{
        DetailsProductViewModel(get(), get())
    }

    viewModel{
        CartViewModel(get(), get())
    }

    viewModel{
        CheckoutViewModel(get())
    }
}