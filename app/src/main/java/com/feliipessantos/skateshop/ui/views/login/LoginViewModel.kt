package com.feliipessantos.skateshop.ui.views.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.listeners.LoginListener
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    private val _login = MutableLiveData<Boolean>()
    val login: LiveData<Boolean> = _login

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg


    fun authUser(email: String, password: String) {
        viewModelScope.launch {
            repository.authUserRepository(email, password, object : LoginListener {
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
            repository.getCurrentUser(object : LoginListener{
                override fun onSuccess() {
                    _login.postValue(true)
                }

                override fun onError(error: String) {
                    _login.postValue(false)
                }

            })
        }
    }
}
