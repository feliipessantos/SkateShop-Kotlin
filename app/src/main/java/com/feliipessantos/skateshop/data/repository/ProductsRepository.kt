package com.feliipessantos.skateshop.data.repository

import com.feliipessantos.skateshop.data.listeners.CartListener
import com.feliipessantos.skateshop.data.listeners.GetCartProductsListener
import com.feliipessantos.skateshop.data.listeners.ProductListListener
import com.feliipessantos.skateshop.data.services.FirebaseFirestoreImpl

class ProductsRepository {
    private val db = FirebaseFirestoreImpl()

    fun getProductListRepository(listener: ProductListListener) = db.getProductList(listener)

    fun addToCart(id: String, img: String, name: String, price: String, listener: CartListener) =
        db.addToCart(id, img, name, price, listener)

    fun getCartProducts(listener: GetCartProductsListener) = db.getCartProducts(listener)

    fun deleteItemCart(listener: GetCartProductsListener) = db.deleteItemCart(listener)


}