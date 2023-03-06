package com.feliipessantos.skateshop.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.feliipessantos.skateshop.DB.DB
import com.example.skateshop.R
import com.example.skateshop.databinding.DetailsProductScreenBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class DetailsProductScreen : AppCompatActivity() {
    private lateinit var binding: DetailsProductScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailsProductScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = intent.extras?.getString("img")
        val name = intent.extras?.getString("name")
        val price = intent.extras?.getString("price")
        //gera id unico cada produto comprado
        val id = UUID.randomUUID().toString()

        Glide.with(this).load(img).into(binding.detailsImg)
        binding.detailsName.text = name
        binding.detailsPrice.text = "$ ${price}"

        binding.btBuy.setOnClickListener {
            if (img != null && name != null && price != null) {
                DB().addToCart(id, img, name, price)
            }
            val intent = Intent(this, CartScreen::class.java)
            // passa id gerado para o carrinho
            intent.putExtra("id", id)
            intent.putExtra("img", img)
            intent.putExtra("name", name)
            intent.putExtra("price", price)
            startActivity(intent)
        }
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

}