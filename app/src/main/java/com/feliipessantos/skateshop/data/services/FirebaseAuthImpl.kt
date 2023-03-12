package com.feliipessantos.skateshop.data.services

import com.feliipessantos.skateshop.data.listeners.LoginListener
import com.feliipessantos.skateshop.data.listeners.RegisterListener
import com.feliipessantos.skateshop.data.listeners.SignOutListener
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthImpl {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestoreImpl()

    fun authUser(email: String, password: String, listener: LoginListener) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { singIng ->
            if (singIng.isSuccessful) {
                listener.onSuccess()

            }
        }.addOnFailureListener {
            listener.onError("Login error")
        }
    }

    fun getCurrentUser(listener: LoginListener){
        val currentUser = auth.currentUser
        if (currentUser != null){
            listener.onSuccess()
        }
    }


    fun registerUser(name: String, email: String, password: String, listener: RegisterListener){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { register ->
            if (register.isSuccessful){
                listener.onSuccess()
                db.saveNameUser(name)
            }
        }.addOnFailureListener {
            listener.onError("Register error")
        }
    }

    fun signOut(listener: SignOutListener){
        auth.signOut()
        listener.onSuccess()
    }
}