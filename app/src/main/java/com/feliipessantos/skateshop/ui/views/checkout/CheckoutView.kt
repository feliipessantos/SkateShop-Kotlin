package com.feliipessantos.skateshop.ui.views.checkout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.skateshop.databinding.CheckoutScreenBinding
import com.feliipessantos.skateshop.ui.views.dialog.DialogDoneShopping
import com.feliipessantos.skateshop.ui.views.products.ProductsView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class CheckoutView : AppCompatActivity() {
    private lateinit var binding: CheckoutScreenBinding
    val dialogDoneShopping = DialogDoneShopping(this)
    private val viewModel: CheckoutViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()

        binding.btFindZIP.setOnClickListener {
            val zip = binding.editZIP.text.toString()
            viewModel.getCep(zip)
        }

        binding.btDone.setOnClickListener {
            val zip = binding.editZIP.text.toString()
            val address = binding.editAddress.text.toString()
            val city = binding.editCity.text.toString()
            val state = binding.editState.text.toString()

            if (zip.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || zip.length < 8) {
                val snackbar = Snackbar.make(
                    it,
                    "Please check your shipping address and try again.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                handlerDialogDone()
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, ProductsView::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }

    private fun observers() {
        viewModel.apiData.observe(this, Observer { data ->
            if (data.isSuccessful) {
                val response = data.body()
                if (response != null) {
                    val address = response.logradouro.toString()
                    val city = response.localidade.toString()
                    val state = response.uf.toString()
                    setForm(address, city, state)
                }
            }
        })

        viewModel.errorMsg.observe(this, Observer { erro ->
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                erro,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.show()
        })

    }

    private fun setForm(address: String, city: String, state: String) {
        binding.editAddress.setText(address)
        binding.editCity.setText(city)
        binding.editState.setText(state)
    }

    private fun handlerDialogDone() {
        dialogDoneShopping.DialogDoneShoppingInit()
        Handler(Looper.getMainLooper()).postDelayed({
            dialogDoneShopping.DialogDoneShoppingFinish()
        }, 2000)
    }
}
