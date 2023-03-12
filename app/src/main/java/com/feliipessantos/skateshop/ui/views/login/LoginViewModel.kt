package com.feliipessantos.skateshop.ui.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.listeners.LoginListener
import com.feliipessantos.skateshop.data.listeners.SignOutListener
import com.feliipessantos.skateshop.data.listeners.UserNameListener
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg


    fun authUser(email: String, password: String) {
        viewModelScope.launch {
            authRepository.authUserRepository(email, password, object : LoginListener {
                override fun onSuccess() {
                    _login.postValue(true)
                }

                override fun onError(error: String) {
                    _login.postValue(false)
                    _errorMsg.postValue(error)
                }
            })
        }
    }

    fun getCurrentUser(){
        viewModelScope.launch {
            authRepository.getCurrentUser(object : LoginListener {
                override fun onSuccess() {
                    _login.postValue(true)
                }

                override fun onError(error: String) {
                    _login.postValue(false)
                }

            })
        }
    }

    fun getUserName(){
        viewModelScope.launch {
            productsRepository.getUsername(object : UserNameListener{
                override fun onSuccess(userName: String) {
                    _userName.postValue(userName)
                }
            })
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut(object : SignOutListener {
                override fun onSuccess() {
                    _login.postValue(false)
                }

            })
        }
    }
}
