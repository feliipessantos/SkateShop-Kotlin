package com.feliipessantos.skateshop.ui.views.checkout

import DialogDoneShoppingFragment
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
import com.example.skateshop.databinding.FragmentCheckoutBinding
import com.feliipessantos.skateshop.ui.views.dialog.DialogLoadingFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class CheckoutFragment : Fragment() {
    private lateinit var _binding : FragmentCheckoutBinding
    private val viewModel: CheckoutViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        _binding.btFindZIP.setOnClickListener {
            val zip = _binding.editZIP.text.toString()
            viewModel.getCep(zip)
        }

        _binding.btDone.setOnClickListener {
            val zip = _binding.editZIP.text.toString()
            val address = _binding.editAddress.text.toString()
            val city = _binding.editCity.text.toString()
            val state = _binding.editState.text.toString()

            if (zip.isEmpty() || address.isEmpty() || city.isEmpty() || state.isEmpty() || zip.length < 8) {
                val snackbar = Snackbar.make(
                    requireView(),
                    "Please check your shipping address and try again.",
                    Snackbar.LENGTH_LONG
                )
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            } else {
                handlerDialog()
            }
        }
    }

    private fun observers() {
        viewModel.apiData.observe(viewLifecycleOwner, Observer { data ->
            if (data.isSuccessful) {
                val response = data.body()
                if (response != null) {
                    val address = response.logradouro.toString()
                    val city = response.localidade.toString()
                    val state = response.uf.toString()
                    setForm(address, city, state)
                }
            }
        })

        viewModel.errorMsg.observe(viewLifecycleOwner, Observer { error ->
            val snackbar = Snackbar.make(
                requireView(),
                error,
                Snackbar.LENGTH_SHORT
            )
            snackbar.setBackgroundTint(Color.RED)
            snackbar.setActionTextColor(Color.WHITE)
            snackbar.show()
        })

    }

    private fun setForm(address: String, city: String, state: String) {
        _binding.editAddress.setText(address)
        _binding.editCity.setText(city)
        _binding.editState.setText(state)
    }

    private fun handlerDialog() {
        val dialog = DialogDoneShoppingFragment()
        dialog.show(parentFragmentManager, "")
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            findNavController().navigate(R.id.action_checkoutFragment_to_productsFragment)
        }, 2500)
    }
}