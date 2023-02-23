package com.example.skateshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.skateshop.databinding.ActivityProductsBinding
import com.google.firebase.auth.FirebaseAuth

class ProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btSingOut.setOnClickListener {
            SingOut()
        }
    }


    private fun SingOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}