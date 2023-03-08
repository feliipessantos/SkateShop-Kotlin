package com.feliipessantos.skateshop.ui.views.detailsProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.listeners.CartListener
import com.feliipessantos.skateshop.data.listeners.SignOutListener
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import com.feliipessantos.skateshop.domain.model.Product
import kotlinx.coroutines.launch

class DetailsProductViewModel(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _addCart = MutableLiveData<Boolean>()
    val addCart: LiveData<Boolean> = _addCart

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut(object : SignOutListener {
                override fun onSuccess() {
                    _login.postValue(false)
                }

            })
        }
    }

    fun addTocart(id: String, img: String, name: String, price: String) { viewModelScope.launch {
        productsRepository.addToCart(id, img, name, price, object : CartListener{
            override fun onSuccess() {
                _addCart.postValue(true)
            }

        })

    }
    }

}