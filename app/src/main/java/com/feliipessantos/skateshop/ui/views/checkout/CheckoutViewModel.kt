package com.feliipessantos.skateshop.ui.views.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.repository.ViaCepRepository
import com.feliipessantos.skateshop.domain.model.Address
import kotlinx.coroutines.launch
import retrofit2.Response

class CheckoutViewModel(private val repository: ViaCepRepository) : ViewModel() {
    private val _apiData = MutableLiveData<Response<Address>>()
    val apiData: LiveData<Response<Address>> = _apiData

    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String> = _errorMsg

    fun getCep(cep: String) {
        viewModelScope.launch {
            val request = repository.getCep(cep)
            if (request.code() == 200) {
                _apiData.postValue(request)
            } else {
                _errorMsg.postValue("Cep not found")
            }
        }
    }
}