package com.feliipessantos.skateshop.ui.views.detailsProduct

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.skateshop.databinding.FragmentDetailsProductBinding
import com.feliipessantos.skateshop.ui.views.cart.CartFragment
import com.feliipessantos.skateshop.ui.views.cart.CartFragmentArgs
import com.feliipessantos.skateshop.ui.views.products.ProductsFragmentDirections
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class DetailsProductFragment : Fragment() {
    private lateinit var _binding: FragmentDetailsProductBinding
    private val viewModel: DetailsProductViewModel by viewModel()
    private val args: DetailsProductFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsProductBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        observers()
        Glide.with(view).load(args.img).into(_binding.detailsImg)
        _binding.detailsName.text = args.name
        _binding.detailsPrice.text = args.price


        _binding.btBuy.setOnClickListener {
            val id = UUID.randomUUID().toString()
            val img = args.img
            val name = args.name
            val price = args.price
            viewModel.addTocart(id, img, name, price)
            val action = DetailsProductFragmentDirections.actionDetailsProductFragmentToCartFragment(
                img = img,
                name = name,
                price = price
            )
            findNavController().navigate(action)
        }
    }

    private fun observers() {
        viewModel.addCart.observe(viewLifecycleOwner, Observer { addToCart ->
            if (addToCart == true) {
                val snackbar =
                    Snackbar.make(
                        requireView(),
                        "Product add to cart",
                        Snackbar.LENGTH_SHORT
                    )
                snackbar.setBackgroundTint(Color.GREEN)
                snackbar.setActionTextColor(Color.WHITE)
                snackbar.show()
            }
        })
    }
}