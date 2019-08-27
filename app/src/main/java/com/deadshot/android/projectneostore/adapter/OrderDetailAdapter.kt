package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.databinding.LayoutOrderDetailBinding
import com.deadshot.android.projectneostore.models.OrderDetail
import com.deadshot.android.projectneostore.models.ProductsInOrder

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class OrderDetailAdapter: ListAdapter<ProductsInOrder, OrderDetailAdapter.OrderDetailViewHolder>(DiffCallback){

    /**
     * The [OrderDetailViewHolder] constructor takes the binding variable from the associated
     * layout_may_cart, which nicely gives it access to the full [OrderDetail] information.
     */
    class OrderDetailViewHolder(private val binding: LayoutOrderDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            productsInOrder: ProductsInOrder
        ){
            binding.productsInOrder = productsInOrder
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): OrderDetailViewHolder {
        return OrderDetailViewHolder(LayoutOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder , position: Int) {
        val productsInOrder = getItem(position)
        holder.bind(productsInOrder)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ProductsInOrder]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ProductsInOrder>(){
        override fun areItemsTheSame(oldItem: ProductsInOrder , newItem: ProductsInOrder): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductsInOrder , newItem: ProductsInOrder): Boolean {
            return oldItem.id == newItem.id
        }

    }

}