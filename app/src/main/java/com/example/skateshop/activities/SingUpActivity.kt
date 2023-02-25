package com.example.skateshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.skateshop.DB.DB
import com.example.skateshop.databinding.ActivitySingUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        val db = DB()

        binding.btSingUp.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val password = binding.editPass.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                val snackbar = Snackbar.make(it,
                    "Please check your data and try again.",
                    Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            db.saveUserData(name)
                            val snackbar =
                                Snackbar.make(it, "Register Successfull", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.GREEN)
                            snackbar.setActionTextColor(Color.WHITE)
                            snackbar.show()
                            val handler = Handler(Looper.getMainLooper())
                            handler.postDelayed({
                                val intent = Intent(this, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }, 1500)
                        } else {
                            val snackbar = Snackbar.make(it, "Error", Snackbar.LENGTH_SHORT)
                            snackbar.setBackgroundTint(Color.RED)
                            snackbar.setActionTextColor(Color.WHITE)
                            snackbar.show()
                        }
                    }.addOnFailureListener { errorSingUp ->
                        val erroMsg = when(errorSingUp){
                            is FirebaseAuthWeakPasswordException -> "Your password must have min 6 characters"
                            is FirebaseAuthUserCollisionException -> "This account is already exist"
                            is FirebaseNetworkException -> "No internet connection"
                            else -> "Register error"
                        }
                        val snackbar = Snackbar.make(it, erroMsg, Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.show()
                    }
            }
        }
    }
}