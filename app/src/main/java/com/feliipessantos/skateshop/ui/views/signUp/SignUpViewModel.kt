package com.feliipessantos.skateshop.ui.views.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.repository.AuthRepository
import com.feliipessantos.skateshop.data.listeners.RegisterListener
import kotlinx.coroutines.launch

class SignUpViewModel(private val repository: AuthRepository): ViewModel() {
    private val _register = MutableLiveData<Boolean>()
    val register: LiveData<Boolean> = _register

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    fun registerUser(email: String, password: String){
        viewModelScope.launch {
            repository.registerUser(email, password, object : RegisterListener{
                override fun onSuccess() {
                    _register.postValue(true)
                }

                override fun onError(error: String) {
                    _register.postValue(false)
                    _errorMsg.postValue(error)
                }

            })
        }
    }
}