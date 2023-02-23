package com.example.skateshop

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skateshop.adapter.ProductAdapter
import com.example.skateshop.databinding.ActivityProductsBinding
import com.example.skateshop.model.Product
import com.google.firebase.auth.FirebaseAuth

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding
    lateinit var productAdapter: ProductAdapter
    var product_list: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#00204B")

        val recycler_products = binding.recyclerProducts

        recycler_products.layoutManager = GridLayoutManager(this, 2)
        recycler_products.setHasFixedSize(true)
        productAdapter = ProductAdapter(this, product_list)
        recycler_products.adapter = productAdapter

        DB().getProductList(product_list, productAdapter)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        R.id.cart -> {
            val intent = Intent(this, CartActivity::class.java)
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
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}