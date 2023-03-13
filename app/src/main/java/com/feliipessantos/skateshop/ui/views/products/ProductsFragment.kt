package com.feliipessantos.skateshop.ui.views.products

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skateshop.databinding.FragmentProductsBinding
import com.feliipessantos.skateshop.data.listeners.GetBrandListener
import com.feliipessantos.skateshop.ui.views.dialog.DialogLoadingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment(), GetBrandListener {
    private lateinit var _binding: FragmentProductsBinding
    lateinit var productAdapter: ProductAdapter
    lateinit var brandsAdapter: BrandsAdapter
    private val viewModel: ProductViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlerDialog()
        observers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBrandsList()
        viewModel.getProductList("Element")

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observers() {
        viewModel.productList.observe(viewLifecycleOwner, Observer { productList ->
            if (productList != null) {
                val recyclerProducts = _binding.recyclerProducts
                recyclerProducts.setHasFixedSize(true)
                productAdapter = ProductAdapter(requireContext(), productList)
                recyclerProducts.adapter = productAdapter
                productAdapter.notifyDataSetChanged()
            }
        })

        viewModel.brandList.observe(viewLifecycleOwner, Observer { brandsList ->
            if (brandsList != null) {
                val recyclerBrands = _binding.recyclerBrands
                recyclerBrands.setHasFixedSize(true)
                recyclerBrands.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                brandsAdapter = BrandsAdapter(this, requireContext(), brandsList)
                recyclerBrands.adapter = brandsAdapter
                brandsAdapter.notifyDataSetChanged()
            }
        })
    }


    private fun handlerDialog() {
        val dialog = DialogLoadingFragment()
        dialog.show(parentFragmentManager, "")
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 1000)
    }

    override fun getBrand(brand: String) {
        viewModel.getProductList(brand)
        _binding.txtBrandProducts.text = "${brand} Products"

    }
}