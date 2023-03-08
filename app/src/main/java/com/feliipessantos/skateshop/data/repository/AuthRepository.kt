package com.feliipessantos.skateshop.data.repository

import com.feliipessantos.skateshop.data.listeners.LoginListener
import com.feliipessantos.skateshop.data.listeners.RegisterListener
import com.feliipessantos.skateshop.data.listeners.SignOutListener
import com.feliipessantos.skateshop.data.services.FirebaseAuthImpl

class AuthRepository {
    private val auth = FirebaseAuthImpl()

    fun authUserRepository(email: String, password: String, listener: LoginListener) =
        auth.authUser(email, password, listener)

    fun getCurrentUser(listener: LoginListener) = auth.getCurrentUser(listener)

    fun registerUser(email: String, password: String, listener: RegisterListener) =
        auth.registerUser(email, password, listener)

    fun signOut(listener: SignOutListener) = auth.signOut(listener)

}