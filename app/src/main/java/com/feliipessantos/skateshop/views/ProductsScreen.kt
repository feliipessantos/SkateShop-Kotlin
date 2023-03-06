package com.feliipessantos.skateshop.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.feliipessantos.skateshop.DB.DB
import com.feliipessantos.skateshop.dialog.DialogLoading
import com.example.skateshop.R
import com.feliipessantos.skateshop.adapter.ProductAdapter
import com.example.skateshop.databinding.ProductsScreenBinding
import com.feliipessantos.skateshop.model.Product
import com.google.firebase.auth.FirebaseAuth

class ProductsScreen : AppCompatActivity() {
    private lateinit var binding: ProductsScreenBinding
    lateinit var productAdapter: ProductAdapter
    var product_list: MutableList<Product> = mutableListOf()
    val dialogLoading = DialogLoading(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_products = binding.recyclerProducts

        recycler_products.layoutManager = GridLayoutManager(this, 2)
        recycler_products.setHasFixedSize(true)
        productAdapter = ProductAdapter(this, product_list)
        recycler_products.adapter = productAdapter

        handlerDialog()
        DB().getProductList(product_list, productAdapter)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
          R.id.cart -> {
            val intent = Intent(this, CartScreen::class.java)
            startActivity(intent)
            true
        }
        R.id.logout -> {
            SingOut()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun SingOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginScreen::class.java)
        startActivity(intent)
        finish()
    }

    private fun handlerDialog(){
        dialogLoading.DialogLoadingInit()
        Handler(Looper.getMainLooper()).postDelayed({
            dialogLoading.DialogLoadingFinish()
        }, 1500)
    }
}