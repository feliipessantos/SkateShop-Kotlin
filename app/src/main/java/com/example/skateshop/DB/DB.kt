package com.example.skateshop.DB

import android.annotation.SuppressLint
import android.util.Log
import com.example.skateshop.adapter.CartAdapter
import com.example.skateshop.adapter.ProductAdapter
import com.example.skateshop.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB {
    val userId = FirebaseAuth.getInstance().currentUser!!.uid
    val db = FirebaseFirestore.getInstance()

    fun saveUserData(user_name: String) {
        val users = hashMapOf(
            "user_name" to user_name
        )
        val documentReference: DocumentReference = db.collection("Users").document(userId)
        documentReference.set(users)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getProductList(product_list: MutableList<Product>, productAdapter: ProductAdapter){
        FirebaseFirestore.getInstance().collection("Products").get()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    for(document in task.result!!){
                        product_list.add(document.toObject(Product::class.java))
                        productAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    fun addToCart(img: String, name: String, price: String){
        val purchased_product = hashMapOf(
            "img" to img,
            "name" to name,
            "price" to price
        )
        val collectionReference: CollectionReference = db.collection("Users").document(userId).collection("purchased_product")
        collectionReference.add(purchased_product)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPurchesedProducts(cart_list: MutableList<Product>, cartAdapter: CartAdapter){
        FirebaseFirestore.getInstance().collection("Users").document(userId)
            .collection("purchased_product").get()
            .addOnCompleteListener { it ->
                if(it.isSuccessful){
                    for (document in it.result!!){
                        cart_list.add(document.toObject(Product::class.java))
                        cartAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    fun deleteItensCart(){
        val productId = FirebaseFirestore.getInstance().collection("Users").document(userId)
            .collection("purchased_product").document("LxBz7q3MHO7OlDyK3iI5")
        productId.delete()
    }
}
