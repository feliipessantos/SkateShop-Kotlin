package com.feliipessantos.skateshop.ui.views.products

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.skateshop.databinding.BrandItemBinding
import com.feliipessantos.skateshop.data.listeners.GetBrandListener
import com.feliipessantos.skateshop.domain.model.Brands

class BrandsAdapter(
    val listener: GetBrandListener,
    val context: Context,
    val brandsList: MutableList<Brands>
) : RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder>() {
    private var selectedItemPosition: Int = 0

    inner class BrandsViewHolder(binding: BrandItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val img = binding.brandImg
        val name = binding.brandName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandsViewHolder {
        val listItem = BrandItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BrandsViewHolder(listItem)
    }

    override fun getItemCount() = brandsList.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: BrandsViewHolder, position: Int) {
        Glide.with(context).load(brandsList[position].img).into(holder.img)
        holder.name.text = brandsList[position].name

        holder.itemView.setOnClickListener {
            selectedItemPosition = holder.adapterPosition
            notifyDataSetChanged()
            listener.getBrand(holder.name.text.toString())
    }
        if (selectedItemPosition == position)
            holder.img.alpha = 1F
        else
            holder.img.alpha = 0.2F
    }
}
