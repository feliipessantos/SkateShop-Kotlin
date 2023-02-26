package com.example.skateshop.DB

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.TextView
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
    val productCollection: CollectionReference =
        db.collection("Users").document(userId).collection("purchased_product")

    fun saveUserData(user_name: String) {
        val users = hashMapOf(
            "user_name" to user_name
        )
        val documentReference: DocumentReference = db.collection("Users").document(userId)
        documentReference.set(users)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getProductList(product_list: MutableList<Product>, productAdapter: ProductAdapter) {
        db.collection("Products").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        product_list.add(document.toObject(Product::class.java))
                        productAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    fun addToCart(id: String, img: String, name: String, price: String) {
        val purchased_product = hashMapOf(
            "id" to id,
            "img" to img,
            "name" to name,
            "price" to price
        )
        productCollection.add(purchased_product)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getPurchesedProducts(cart_list: MutableList<Product>, cartAdapter: CartAdapter) {
        db.collection("Users").document(userId)
            .collection("purchased_product").get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        cart_list.add(document.toObject(Product::class.java))
                        cartAdapter.notifyDataSetChanged()
                    }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun deleteItensCart(cartAdapter: CartAdapter) {
        productCollection.get().addOnSuccessListener { result ->
            for (document in result) {
                val id = document.id
                productCollection.document(id).delete()
                cartAdapter.notifyDataSetChanged()
            }
        }
    }

    fun getUserName(user_name: TextView) {
        db.collection("Users").document(userId)
            .addSnapshotListener { document, error ->
                if (document != null) {
                    user_name.text = "Hey ${document.getString("name").toString() }, please select a shipping address"
                }
            }
    }
}

