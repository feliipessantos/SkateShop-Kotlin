package com.feliipessantos.skateshop.data.listeners

import com.feliipessantos.skateshop.domain.model.Product

interface ProductListListener {
    fun getProductList(productList: MutableList<Product>)
}