package com.feliipessantos.skateshop.ui.views.detailsProduct

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.skateshop.R
import com.example.skateshop.databinding.DetailsProductScreenBinding
import com.feliipessantos.skateshop.ui.views.cart.CartView
import com.feliipessantos.skateshop.ui.views.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailsProductView : AppCompatActivity() {
    private lateinit var binding: DetailsProductScreenBinding
    private val viewModel: DetailsProductViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = intent.extras?.getString("img")
        val name = intent.extras?.getString("name")
        val price = intent.extras?.getString("price")
        val id = UUID.randomUUID().toString()

        Glide.with(this).load(img).into(binding.detailsImg)
        binding.detailsName.text = name
        binding.detailsPrice.text = "$ ${price}"

        obervers()

        binding.btBuy.setOnClickListener {
            if (img != null && name != null && price != null) {
                viewModel.addTocart(id, img, name, price)
            }
            val intent = Intent(this, CartView::class.java)

            intent.putExtra("id", id)
            intent.putExtra("img", img)
            intent.putExtra("name", name)
            intent.putExtra("price", price)
            startActivity(intent)

        }
    }

    private fun obervers() {
        viewModel.addCart.observe(this, Observer { addToCart ->
            if (addToCart == true) {
                val snackbar =
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Product add to cart",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.cart -> {
            val intent = Intent(this, CartView::class.java)
            startActivity(intent)
            true
        }
        R.id.logout -> {
            singOut()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun singOut() {
        viewModel.signOut()
        val intent = Intent(this, LoginViewModel::class.java)
        startActivity(intent)
        finish()
    }

}