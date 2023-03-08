package com.feliipessantos.skateshop.ui.views.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.listeners.GetCartProductsListener
import com.feliipessantos.skateshop.data.listeners.SignOutListener
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import com.feliipessantos.skateshop.domain.model.Product
import kotlinx.coroutines.launch

class CartViewModel(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _productList = MutableLiveData<MutableList<Product>>()
    val productList: LiveData<MutableList<Product>> = _productList

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut(object : SignOutListener {
                override fun onSuccess() {
                    _login.postValue(false)
                }

            })
        }
    }

    fun getCartProducts(){viewModelScope.launch {
        productsRepository.getCartProducts(object : GetCartProductsListener{
            override fun getCartProductsLisntener(cartList: MutableList<Product>) {
                _productList.postValue(cartList)
            }

        })
    }}

    fun deleteItemCart(){ viewModelScope.launch {
        productsRepository.deleteItemCart(object : GetCartProductsListener{
            override fun getCartProductsLisntener(cartList: MutableList<Product>) {
                _productList.postValue(cartList)
            }

        })
    }}
}