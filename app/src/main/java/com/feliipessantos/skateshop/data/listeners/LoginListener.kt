package com.feliipessantos.skateshop.data.listeners

interface LoginListener {
    fun onSuccess()

    fun onError(error: String)
}