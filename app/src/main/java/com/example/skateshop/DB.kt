package com.example.skateshop

import android.annotation.SuppressLint
import android.util.Log
import com.example.skateshop.adapter.ProductAdapter
import com.example.skateshop.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class DB {
    fun saveUserData(name: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()

        val users = hashMapOf(
            "name" to name
        )

        val documentReference: DocumentReference = db.collection("Users").document(userId)
        documentReference.set(users).addOnSuccessListener {
            Log.d("DB", "Sucess")
        }.addOnFailureListener { erro ->
            Log.d("DB", "Error ${erro.printStackTrace()}")
        }
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
}
