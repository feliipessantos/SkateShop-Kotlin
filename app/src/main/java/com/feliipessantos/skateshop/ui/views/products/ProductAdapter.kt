package com.feliipessantos.skateshop.ui.views.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skateshop.R
import com.example.skateshop.databinding.ProductItemBinding
import com.feliipessantos.skateshop.domain.model.Product

class ProductAdapter(val context: Context, val product_list: MutableList<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun getItemCount() = product_list.size

    inner class ProductViewHolder(binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.productImg
        val name = binding.productName
        val price = binding.productPrice


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val listItem = ProductItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ProductViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Glide.with(context).load(product_list[position].img).into(holder.img)
        holder.name.text = product_list.get(position).name
        holder.price.text = "$ ${product_list.get(position).price}"

        holder.itemView.setOnClickListener {
            val action = ProductsFragmentDirections.actionProductsFragmentToDetailsProductFragment(
                img = product_list[position].img.toString(),
                name = product_list[position].name.toString(),
                price = product_list[position].price.toString()
            )
            findNavController(holder.itemView).navigate(action)

        }
    }
}