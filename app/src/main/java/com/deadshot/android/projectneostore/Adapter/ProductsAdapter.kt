package com.deadshot.android.projectneostore.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.LayoutProductBinding
import com.deadshot.android.projectneostore.models.ProductList

class ProductsAdapter: ListAdapter<ProductList, ProductsAdapter.ProductListViewHolder>(DiffCallback){
    class ProductListViewHolder(private var binding: LayoutProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productList: ProductList){
            binding.property = productList
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProductList>(){
        override fun areItemsTheSame(oldItem: ProductList, newItem: ProductList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductList, newItem: ProductList): Boolean {
            return oldItem.productId == newItem.productId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ProductListViewHolder {
        return ProductListViewHolder(LayoutProductBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ProductListViewHolder, position: Int) {
        val productList = getItem(position)
        holder.bind(productList)
    }
}