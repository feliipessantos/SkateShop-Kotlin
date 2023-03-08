package com.feliipessantos.skateshop.data.listeners

import com.feliipessantos.skateshop.domain.model.Product

interface GetCartProductsListener {
    fun getCartProductsLisntener(cartList: MutableList<Product>)
}