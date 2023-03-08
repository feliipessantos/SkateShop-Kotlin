package com.feliipessantos.skateshop.ui.views.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skateshop.databinding.SplashScreenBinding
import com.feliipessantos.skateshop.ui.views.login.LoginView
import com.ncorti.slidetoact.SlideToActView


class SplashScreen : AppCompatActivity() {
    private lateinit var binding: SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val slide = binding.slideBt
        val intent = Intent(this, LoginView::class.java)


        slide.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {
            override fun onSlideComplete(view: SlideToActView) {
                startActivity(intent)
                finish()
            }
        }
        supportActionBar!!.hide()

    }
}
