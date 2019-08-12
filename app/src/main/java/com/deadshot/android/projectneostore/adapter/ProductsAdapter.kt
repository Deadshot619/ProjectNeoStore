package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.databinding.LayoutProductBinding
import com.deadshot.android.projectneostore.models.ProductList

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class ProductsAdapter: ListAdapter<ProductList, ProductsAdapter.ProductListViewHolder>(DiffCallback){

    /**
     * The ProductListViewHolder constructor takes the binding variable from the associated
     * Product_layout, which nicely gives it access to the full [ProductList] information.
     */
    class ProductListViewHolder(private var binding: LayoutProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productList: ProductList){
            binding.property = productList
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ProductList]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ProductList>(){
        override fun areItemsTheSame(oldItem: ProductList, newItem: ProductList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductList, newItem: ProductList): Boolean {
            return oldItem.productId == newItem.productId
        }

    }


    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsAdapter.ProductListViewHolder {
        return ProductListViewHolder(LayoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ProductsAdapter.ProductListViewHolder, position: Int) {
        val productList = getItem(position)
        holder.bind(productList)
    }
}