package com.feliipessantos.skateshop.ui.views.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.skateshop.databinding.LoginScreenBinding
import com.feliipessantos.skateshop.ui.views.signUp.SignUpView
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
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginView : AppCompatActivity() {
    private lateinit var binding: LoginScreenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        window.statusBarColor = Color.parseColor("#DEE2D6")



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
                viewModel.authUser(email, password)
            }
        }
        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this, SignUpView::class.java)
            startActivity(intent)
        }
        binding.txtSignInGoogle.setOnClickListener {
            singIn()
        }

        oberservers()
    }

    private fun oberservers() {
        viewModel.login.observe(this, Observer { login ->
            if (login) {
                navigateToProductsActivity()
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

    private fun singIn() {
        val intent = googleSingInClient.signInIntent
        launchActivity.launch(intent)
    }

    var launchActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
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

    }

    override fun onStart() {
        super.onStart()

        viewModel.getCurrentUser()
    }
}