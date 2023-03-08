package com.feliipessantos.skateshop.ui.views.products

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skateshop.R
import com.example.skateshop.databinding.ProductsScreenBinding
import com.feliipessantos.skateshop.ui.views.cart.CartView
import com.feliipessantos.skateshop.ui.views.dialog.DialogLoading
import com.feliipessantos.skateshop.ui.views.login.LoginView
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductsView : AppCompatActivity() {
    private lateinit var binding: ProductsScreenBinding
    lateinit var productAdapter: ProductAdapter
    private val viewModel: ProductViewModel by viewModel()

    val dialogLoading = DialogLoading(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductsScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        handlerDialog()
        observers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductList()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observers() {
        viewModel.productList.observe(this, Observer { productList ->
            if (productList != null) {
                val recyclerProducts = binding.recyclerProducts
                recyclerProducts.layoutManager = GridLayoutManager(this, 2)
                recyclerProducts.setHasFixedSize(true)
                productAdapter = ProductAdapter(this, productList)
                recyclerProducts.adapter = productAdapter
                productAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.cart -> {
            val intent = Intent(this, CartView::class.java)
            startActivity(intent)
            true
        }
        R.id.logout -> {
            SingOut()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun SingOut(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginView::class.java)
        startActivity(intent)
        finish()
    }

    private fun handlerDialog(){
        dialogLoading.DialogLoadingInit()
        Handler(Looper.getMainLooper()).postDelayed({
            dialogLoading.DialogLoadingFinish()
        }, 1500)
    }
}