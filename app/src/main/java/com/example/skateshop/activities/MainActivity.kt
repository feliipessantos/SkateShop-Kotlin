package com.example.skateshop.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.skateshop.databinding.ActivityMainBinding
import com.example.skateshop.Dialog.DialogLoading
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("815462953710-410iavg6dn8v3icrm3sne57ij260abh3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSingInClient = GoogleSignIn.getClient(this, gso)

        binding.btSignIn.setOnClickListener { view ->
            val email = binding.editEmail.text.toString()
            val password = binding.editPass.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                val snackbar =
                    Snackbar.make(view,
                        "Please check your username and password and try again.",
                        Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navigateToProductsActivity()
                        }
                    }.addOnFailureListener {
                        val snackbar = Snackbar.make(view, "Error login", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.setActionTextColor(Color.WHITE)
                        snackbar.show()
                    }
            }
        }
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SingUpActivity::class.java)
            startActivity(intent)
        }
        binding.txtSignInGoogle.setOnClickListener {
            singIn()
        }
    }

    private fun singIn() {
        val intent = googleSingInClient.signInIntent
        launchActivity.launch(intent)
    }

    var launchActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if(result.resultCode == RESULT_OK){
            val int = result.data
            val taks = GoogleSignIn.getSignedInAccountFromIntent(int)
            try {
                val account = taks.getResult(ApiException::class.java)
                signInWithGoogle(account.idToken!!)
            } catch (_: ApiException){

            }
        }
    }

    private fun signInWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) {
            task: Task<AuthResult> ->
            if(task.isSuccessful){
                navigateToProductsActivity()
            }else{
                val snackbar = Snackbar.make(findViewById(android.R.id.content), "Error login", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            }
        }
    }

    private fun navigateToProductsActivity() {
        val intent = Intent(this, ProductsActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            navigateToProductsActivity()
        }
    }
}