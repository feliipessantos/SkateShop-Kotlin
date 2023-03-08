package com.feliipessantos.skateshop.ui.views.signUp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.skateshop.databinding.SingUpScreenBinding
import com.feliipessantos.skateshop.ui.views.login.LoginView
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpView : AppCompatActivity() {
    private lateinit var binding: SingUpScreenBinding
    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SingUpScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#DEE2D6")

        binding.btSingUp.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPass.text.toString()
            val view: View? = this.currentFocus

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() && view != null) {
                val inputMethodManager =
                    getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                if (view != null) {
                    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                }
                val snackbar = Snackbar.make(
                    it,
                    "Please check your data and try again.",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                viewModel.registerUser(email, password)
            }
        }
        observers()
    }

    private fun observers() {
        viewModel.register.observe(this, Observer { register ->
            if (register == true) {
                val snackbar =
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Register Successfull",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    val intent = Intent(this, LoginView::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)
            }
        })

        viewModel.errorMsg.observe(this, Observer { errorMsg ->
            val snackbar =
                Snackbar.make(findViewById(android.R.id.content), errorMsg, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.show()
        })
    }
}
