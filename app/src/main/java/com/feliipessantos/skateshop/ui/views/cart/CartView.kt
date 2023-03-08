package com.feliipessantos.skateshop.ui.views.cart

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skateshop.R
import com.example.skateshop.databinding.CartScreenBinding
import com.feliipessantos.skateshop.ui.views.checkout.CheckoutView
import com.feliipessantos.skateshop.ui.views.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartView : AppCompatActivity() {
    private lateinit var binding: CartScreenBinding
    lateinit var cartAdapter: CartAdapter
    private val viewModel: CartViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CartScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        observers()

        binding.btClear.setOnClickListener {
                viewModel.deleteItemCart()
        }

        binding.btFinish.setOnClickListener {
                val intent = Intent(this, CheckoutView::class.java)
                startActivity(intent)
                finish()

        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getCartProducts()
    }
    private fun observers(){
        viewModel.productList.observe(this, Observer { cartList ->
            if (cartList != null){
                val recyclerCart = binding.recyclerCart

                recyclerCart.layoutManager = GridLayoutManager(this, 1)
                recyclerCart.setHasFixedSize(true)
                cartAdapter = CartAdapter(this, cartList)
                recyclerCart.adapter = cartAdapter
                cartAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.logout -> {
            singOut()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun singOut() {
        viewModel.signOut()
        val intent = Intent(this, LoginViewModel::class.java)
        startActivity(intent)
        finish()
    }

}