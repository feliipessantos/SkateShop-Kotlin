package com.feliipessantos.skateshop.ui.views.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.skateshop.R

class DialogLoading(private val activity: Activity) {
    lateinit var dialog: AlertDialog
    fun DialogLoadingInit() {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(R.layout.dialog_loading, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }
    fun DialogLoadingFinish(){
        dialog.dismiss()
    }
}