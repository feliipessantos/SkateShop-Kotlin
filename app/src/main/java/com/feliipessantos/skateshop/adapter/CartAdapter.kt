package com.feliipessantos.skateshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skateshop.databinding.CartItemBinding
import com.feliipessantos.skateshop.model.Product

class CartAdapter(val context: Context, val cart_list: MutableList<Product>) :
    RecyclerView.Adapter<CartAdapter.CartProductViewHolder>() {

    inner class CartProductViewHolder(binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.imgCartItem
        val name = binding.nameCartItem
        val price = binding.priceCartItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
        val listItem = CartItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return CartProductViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        Glide.with(context).load(cart_list.get(position).img).into(holder.img)
        holder.name.text = cart_list.get(position).name
        holder.price.text = "$ ${cart_list.get(position).price}"
        holder.adapterPosition
    }

    override fun getItemCount() = cart_list.size
}

