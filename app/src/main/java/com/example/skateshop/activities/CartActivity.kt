package com.example.skateshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.skateshop.DB.DB
import com.example.skateshop.R
import com.example.skateshop.adapter.CartAdapter
import com.example.skateshop.databinding.ActivityCartBinding
import com.example.skateshop.model.Product
import com.google.firebase.auth.FirebaseAuth

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    lateinit var cartAdapter: CartAdapter
    var cart_list: MutableList<Product> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#00204B")

        val recycler_cart = binding.recyclerCart

        recycler_cart.layoutManager = GridLayoutManager(this, 1)
        recycler_cart.setHasFixedSize(true)
        cartAdapter = CartAdapter(this, cart_list)
        recycler_cart.adapter = cartAdapter

        DB().getPurchesedProducts(cart_list, cartAdapter)

        //captura id dentro do produto
        // precisa colocar botão e rastrar clice nesse id
        // deletar aquele que foi clicado
        // talvez colocar funcao delete dentro do adapter
        val id = intent.extras?.getString("id")

        binding.btClear.setOnClickListener {
                DB().deleteItensCart()
            // atualizar tela apos deletar
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
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