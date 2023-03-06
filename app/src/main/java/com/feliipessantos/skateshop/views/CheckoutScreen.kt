package com.feliipessantos.skateshop.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.feliipessantos.skateshop.API.ZipCodeAPI
import com.feliipessantos.skateshop.DB.DB
import com.feliipessantos.skateshop.dialog.DialogDoneShopping
import com.example.skateshop.databinding.CheckoutScreenBinding
import com.feliipessantos.skateshop.model.Address
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckoutScreen : AppCompatActivity() {
    private lateinit var binding: CheckoutScreenBinding
    val dialogDoneShopping = DialogDoneShopping(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CheckoutScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://viacep.com.br/ws/")
            .build()
            .create(ZipCodeAPI::class.java)

        val user_name = binding.txtCheckoutWelcome

        DB().getUserName(user_name)

        binding.btFindZIP.setOnClickListener {
            val zip = binding.editZIP.text.toString()

            if (zip.isEmpty()) {
                val snackbar = Snackbar.make(
                    it,
                    "Please check your zip code and try again.",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                retrofit.setAddress(zip).enqueue(object : Callback<Address> {
                    override fun onResponse(call: Call<Address>, response: Response<Address>) {
                        if (response.code() == 200) {
                            val address = response.body()?.logradouro.toString()
                            val city = response.body()?.localidade.toString()
                            val state = response.body()?.uf.toString()
                            setFormularios(address, city, state)
                        } else {
                            val snackbar = Snackbar.make(
                                it,
                                "Error to find zip code.",
                                Snackbar.LENGTH_SHORT
                            )
                            snackbar.setBackgroundTint(Color.RED)
                            snackbar.setActionTextColor(Color.WHITE)
                            snackbar.show()
                        }
                    }
                    override fun onFailure(call: Call<Address>, t: Throwable) {
                        val snackbar = Snackbar.make(
                            it,
                            " Error to find zip code.",
                            Snackbar.LENGTH_SHORT
                        )
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.show()
                    }
                })
            }
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
                    val intent = Intent(this, ProductsScreen::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }

    private fun setFormularios(address: String, city: String, state: String) {
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