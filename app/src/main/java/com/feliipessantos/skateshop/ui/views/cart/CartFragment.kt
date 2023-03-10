package com.feliipessantos.skateshop.ui.views.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.skateshop.R
import com.example.skateshop.databinding.FragmentCartBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private lateinit var _binding: FragmentCartBinding
    lateinit var cartAdapter: CartAdapter
    private val viewModel: CartViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observers()

        _binding.btClear.setOnClickListener {
            viewModel.deleteItemCart()
        }

        _binding.btFinish.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getCartProducts()
    }

    private fun observers(){
        viewModel.productList.observe(viewLifecycleOwner, Observer { cartList ->
            if (cartList != null){
                val recyclerCart = _binding.recyclerCart
                recyclerCart.setHasFixedSize(true)
                cartAdapter = CartAdapter(requireContext(), cartList)
                recyclerCart.adapter = cartAdapter
                cartAdapter.notifyDataSetChanged()
            }
        })
    }

}