package com.feliipessantos.skateshop.data.services

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import com.feliipessantos.skateshop.data.listeners.*
import com.feliipessantos.skateshop.domain.model.Brands
import com.feliipessantos.skateshop.domain.model.Product
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseFirestoreImpl {
    val db = FirebaseFirestore.getInstance()
    fun getProductList(listener: ProductListListener, brand: String) {
        val productList: MutableList<Product> = mutableListOf()

        db.collection("Products").document("tk3m5ngv4YwfkXAlpCMu").collection(brand).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        productList.add(document.toObject(Product::class.java))
                        listener.getProductList(productList)
                    }
                }
            }
    }

    fun getBrandsList(listener: BrandsListListener){
        val brandsList: MutableList<Brands> = mutableListOf()

        db.collection("Brands").get()
            .addOnCompleteListener {
                if (it.isSuccessful){
                    for (document in it.result!!){
                        brandsList.add(document.toObject(Brands::class.java))
                        listener.getBrandsList(brandsList)
                    }
                }
            }

    }

    fun addToCart(id: String, img: String, name: String, price: String, listener: CartListener) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val productCollection: CollectionReference =
            db.collection("Users").document(userId).collection("purchased_product")

        val purchasedProduct = hashMapOf(
            "id" to id,
            "img" to img,
            "name" to name,
            "price" to price
        )
        productCollection.add(purchasedProduct)
        listener.onSuccess()
    }

    fun getCartProducts(listener: GetCartProductsListener) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val cartList: MutableList<Product> = mutableListOf()
        db.collection("Users").document(userId)
            .collection("purchased_product").get()
            .addOnCompleteListener { it ->
                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        cartList.add(document.toObject(Product::class.java))
                        listener.getCartProductsLisntener(cartList)
                    }
                } else {
                    listener.onError("add itens")
                }
            }.addOnFailureListener {
                listener.onError("Add itens into cart!")
            }
    }

    fun deleteItemCart(listener: GetCartProductsListener) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val productCollection: CollectionReference =
            db.collection("Users").document(userId).collection("purchased_product")
        val cartList: MutableList<Product> = mutableListOf()

        productCollection.get().addOnSuccessListener { result ->
            for (document in result) {
                val id = document.id
                productCollection.document(id).delete()
                listener.getCartProductsLisntener(cartList)
            }
        }.addOnFailureListener {
            listener.onError("Add itens into cart!")
        }
    }

    fun getUserName(listener: UserNameListener) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val productCollection: DocumentReference =
            db.collection("Users").document(userId)

        productCollection.addSnapshotListener { document, _ ->
            if (document != null) {
                listener.onSuccess(document.getString("name").toString())
            }

        }
    }

    fun saveNameUser(name: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val users = hashMapOf(
            "name" to name
        )
        val documentReference: DocumentReference = db.collection("Users").document(userId)
        documentReference.set(users)


    }

}