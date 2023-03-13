package com.feliipessantos.skateshop.ui.views.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.feliipessantos.skateshop.data.listeners.BrandsListListener
import com.feliipessantos.skateshop.data.repository.ProductsRepository
import com.feliipessantos.skateshop.data.listeners.ProductListListener
import com.feliipessantos.skateshop.domain.model.Brands
import com.feliipessantos.skateshop.domain.model.Product
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductsRepository): ViewModel() {
    private val _productList = MutableLiveData<MutableList<Product>>()
    val productList: LiveData<MutableList<Product>> = _productList

    private val _brandList = MutableLiveData<MutableList<Brands>>()
    val brandList: LiveData<MutableList<Brands>> = _brandList

    private val _brand = MutableLiveData<String>()
    val brand: LiveData<String> = _brand

    fun getProductList(brand: String){ viewModelScope.launch {

        repository.getProductListRepository(object : ProductListListener{
            override fun getProductList(productList: MutableList<Product>) {
                _productList.postValue(productList)

            }

        }, brand)
    }}

    fun getBrandsList(){ viewModelScope.launch {
        repository.getBrandsList(object : BrandsListListener{
            override fun getBrandsList(brandsList: MutableList<Brands>) {
                _brandList.postValue(brandsList)
            }
        })
    }}
}