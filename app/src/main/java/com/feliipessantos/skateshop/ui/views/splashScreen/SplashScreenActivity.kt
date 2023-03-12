package com.feliipessantos.skateshop.ui.views.splashScreen


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skateshop.R
import com.example.skateshop.databinding.ActivitySplashScreenBinding
import com.feliipessantos.skateshop.ui.views.MainActivity
import com.ncorti.slidetoact.SlideToActView

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var _binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        window.statusBarColor = getColor(R.color.blue)

        val slide = _binding.slideBt

        slide.onSlideCompleteListener = object : SlideToActView.OnSlideCompleteListener {

            override fun onSlideComplete(view: SlideToActView) {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
