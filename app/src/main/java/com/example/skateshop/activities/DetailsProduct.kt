package com.example.skateshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.skateshop.DB.DB
import com.example.skateshop.R
import com.example.skateshop.databinding.ActivityDetailsProductBinding
import com.google.firebase.auth.FirebaseAuth

class DetailsProduct : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.statusBarColor = Color.parseColor("#00204B")

        val img = intent.extras?.getString("img")
        val name = intent.extras?.getString("name")
        val price = intent.extras?.getString("price")

        Glide.with(this).load(img).into(binding.detailsImg)
        binding.detailsName.text = name
        binding.detailsPrice.text = "$ ${price}"

        binding.btBuy.setOnClickListener {
            if (img != null && name != null && price != null) {
                    DB().addToCart(img, name, price)
            }
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("img", img)
            intent.putExtra("name", name)
            intent.putExtra("price", price)
            startActivity(intent)
        }

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