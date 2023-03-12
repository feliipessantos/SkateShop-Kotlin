package com.feliipessantos.skateshop.ui.views.login

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skateshop.R
import com.example.skateshop.databinding.FragmentLoginBinding
import com.feliipessantos.skateshop.data.services.FirebaseFirestoreImpl
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


class LoginFragment : Fragment() {
    private lateinit var _binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("815462953710-410iavg6dn8v3icrm3sne57ij260abh3.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSingInClient = GoogleSignIn.getClient(requireActivity(), gso)

        _binding.btSignIn.setOnClickListener { view ->
            val email = _binding.editEmail.text.toString()
            val password = _binding.editPass.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                val snackbar =
                    Snackbar.make(
                        view,
                        "Please check your username and password and try again.",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                viewModel.authUser(email, password)
            }
        }
        _binding.txtSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_singUpFragment)
        }
        _binding.txtSignInGoogle.setOnClickListener {
            singIn()
        }

        observers()
    }

    override fun onStart() {
        super.onStart()

        viewModel.getCurrentUser()
    }


    private fun observers() {
        viewModel.login.observe(viewLifecycleOwner, Observer { login ->
            if (login) {
                findNavController().navigate(R.id.action_loginFragment_to_loggedUserFragment)
            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer { errorMsg ->
            val snackbar =
                Snackbar.make(requireView(), errorMsg, Snackbar.LENGTH_SHORT)
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.show()
        })
    }

    private fun signInWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    val displayName =
                        GoogleSignIn.getLastSignedInAccount(requireContext())?.displayName
                    FirebaseFirestoreImpl().saveNameUser(displayName.toString())
                    findNavController().navigate(R.id.action_loginFragment_to_loggedUserFragment)
                } else {
                    val snackbar = Snackbar.make(
                        requireView(),
                        "Error login",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setActionTextColor(Color.WHITE)
                    snackbar.show()
                }
            }
    }

    private fun singIn() {
        val intent = googleSingInClient.signInIntent
        launchActivity.launch(intent)
    }

    var launchActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val int = result.data
            val taks = GoogleSignIn.getSignedInAccountFromIntent(int)
            try {
                val account = taks.getResult(ApiException::class.java)
                signInWithGoogle(account.idToken!!)
            } catch (_: ApiException) {

            }
        }
    }
}


