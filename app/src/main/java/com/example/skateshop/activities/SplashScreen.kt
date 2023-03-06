package com.example.skateshop.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skateshop.databinding.ActivitySplashScreenBinding
import com.ncorti.slidetoact.SlideToActView


class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slide = binding.slideBt
        val intent = Intent(this, MainActivity::class.java)


        slide.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                startActivity(intent)
                finish()
            }
        }
        supportActionBar!!.hide()

    }
}
