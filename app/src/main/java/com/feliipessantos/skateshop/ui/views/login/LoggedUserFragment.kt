package com.feliipessantos.skateshop.ui.views.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skateshop.R
import com.example.skateshop.databinding.FragmentLoggedUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoggedUserFragment : Fragment() {
    private lateinit var _binding: FragmentLoggedUserBinding
    private val viewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedUserBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oberservers()
        viewModel.getUserName()

        _binding.btSingOut.setOnClickListener {
            viewModel.signOut()
        }
    }

    private fun oberservers() {
        viewModel.login.observe(viewLifecycleOwner, Observer { login ->
            if (!login) {
                findNavController().navigate(R.id.action_loggedUserFragment_to_loginFragment)
            }
        })

        viewModel.userName.observe(viewLifecycleOwner, Observer { userName ->
            if (userName != null) {
                _binding.userName.text = userName
            }
        })
    }
}