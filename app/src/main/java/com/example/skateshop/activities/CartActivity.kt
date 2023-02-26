package com.example.skateshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skateshop.DB.DB
import com.example.skateshop.R
import com.example.skateshop.adapter.CartAdapter
import com.example.skateshop.databinding.ActivityCartBinding
import com.example.skateshop.model.Product
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    lateinit var cartAdapter: CartAdapter
    var cart_list: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_cart = binding.recyclerCart

        recycler_cart.layoutManager = GridLayoutManager(this, 1)
        recycler_cart.setHasFixedSize(true)
        cartAdapter = CartAdapter(this, cart_list)
        recycler_cart.adapter = cartAdapter

        DB().getPurchesedProducts(cart_list, cartAdapter)

        binding.btClear.setOnClickListener {
            if (cart_list.isEmpty()) {
                val snackbar =
                    Snackbar.make(it, "Your cart is already empty.", Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                DB().deleteItensCart(cartAdapter)
                cart_list.removeAll(cart_list)
            }
        }

        binding.btFinish.setOnClickListener {
            if (cart_list.isEmpty()) {
                val snackbar =
                    Snackbar.make(it, "Your cart is empty.", Snackbar.LENGTH_LONG)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                val intent = Intent(this, Checkout::class.java)
                startActivity(intent)
                DB().deleteItensCart(cartAdapter)
                cart_list.removeAll(cart_list)
                finish()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> {
            SingOut()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun SingOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}