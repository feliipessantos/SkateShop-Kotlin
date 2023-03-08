package com.feliipessantos.skateshop.ui.views.dialog

import android.app.Activity
import android.app.AlertDialog
import com.example.skateshop.R

class DialogDoneShopping(private val activity: Activity) {
    lateinit var dialog: AlertDialog

    fun DialogDoneShoppingInit() {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity.layoutInflater
        builder.setView(layoutInflater.inflate(R.layout.dialog_done_shopping, null))
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    fun DialogDoneShoppingFinish() {
        dialog.dismiss()
    }
}