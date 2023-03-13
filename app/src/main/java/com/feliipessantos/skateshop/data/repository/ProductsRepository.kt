package com.feliipessantos.skateshop.data.repository

import com.feliipessantos.skateshop.data.listeners.*
import com.feliipessantos.skateshop.data.services.FirebaseFirestoreImpl

class ProductsRepository {
    private val db = FirebaseFirestoreImpl()

    fun getProductListRepository(listener: ProductListListener, brand: String) = db.getProductList(listener, brand)

    fun getBrandsList(listener: BrandsListListener) = db.getBrandsList(listener)

    fun addToCart(id: String, img: String, name: String, price: String, listener: CartListener) =
        db.addToCart(id, img, name, price, listener)

    fun getCartProducts(listener: GetCartProductsListener) = db.getCartProducts(listener)

    fun deleteItemCart(listener: GetCartProductsListener) = db.deleteItemCart(listener)

    fun getUsername(listener: UserNameListener) = db.getUserName(listener)


}