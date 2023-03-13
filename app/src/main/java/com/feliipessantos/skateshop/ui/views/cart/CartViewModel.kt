package com.feliipessantos.skateshop.ui.views.cart

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.listeners.GetCartProductsListener
import com.feliipessantos.skateshop.data.listeners.LoginListener
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import com.feliipessantos.skateshop.domain.model.Product
import kotlinx.coroutines.launch

class CartViewModel(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _productList = MutableLiveData<MutableList<Product>>()
    val productList: LiveData<MutableList<Product>> = _productList

    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getCartProducts(){viewModelScope.launch {
        productsRepository.getCartProducts(object : GetCartProductsListener{
            override fun getCartProductsLisntener(cartList: MutableList<Product>) {
                _productList.postValue(cartList)
            }

            override fun onError(error: String) {
                _error.postValue(error)
            }
        })
    }}

    fun deleteItemCart(){ viewModelScope.launch {
        productsRepository.deleteItemCart(object : GetCartProductsListener{
            override fun getCartProductsLisntener(cartList: MutableList<Product>) {
                _productList.postValue(cartList)
            }

            override fun onError(error: String) {
                _error.postValue(error)
            }
        })
    }}

    fun getUser(){
        authRepository.getCurrentUser(object : LoginListener{
            override fun onSuccess() {
                _login.postValue(true)
            }

            override fun onError(error: String) {
                _login.postValue(false)
                _error.postValue("No products in cart!!")
            }

        })
    }
}