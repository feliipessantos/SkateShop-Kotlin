package com.feliipessantos.skateshop.ui.views.signUp

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skateshop.R
import com.example.skateshop.databinding.FragmentSingUpBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SingUpFragment : Fragment() {
    private lateinit var _binding: FragmentSingUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSingUpBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.btSingUp.setOnClickListener {
            val name = _binding.editName.text.toString()
            val email = _binding.editEmail.text.toString()
            val password = _binding.editPass.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() && view != null) {
                val snackbar = Snackbar.make(
                    it,
                    "Please check your data and try again.",
                    Snackbar.LENGTH_SHORT
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                viewModel.registerUser(name, email, password)
            }
        }
        observers()
    }

    private fun observers() {
        viewModel.register.observe(viewLifecycleOwner, Observer { register ->
            if (register == true) {
                val snackbar =
                    Snackbar.make(
                       requireView(),
                        "Register Successfull",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
                val handler = Handler(Looper.getMainLooper())
                handler.postDelayed({
                    findNavController().navigate(R.id.action_singUpFragment_to_productsFragment)
                }, 1000)
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

}