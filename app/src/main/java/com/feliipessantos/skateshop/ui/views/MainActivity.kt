package com.feliipessantos.skateshop.ui.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.skateshop.R
import com.example.skateshop.databinding.ActivityMainBinding
import com.feliipessantos.skateshop.ui.views.products.ProductsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(_binding.fragmentContainerView.id) as NavHostFragment)

        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph, )
        _binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        _binding.bottomToolbar.setupWithNavController(navController)

    }
}