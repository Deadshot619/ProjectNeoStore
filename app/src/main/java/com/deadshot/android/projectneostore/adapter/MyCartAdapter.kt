package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.databinding.LayoutMyCartItemBinding
import com.deadshot.android.projectneostore.models.ProductsInfo

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class MyCartAdapter : ListAdapter<ProductsInfo, MyCartAdapter.MyCartViewHolder>(DiffCallback){

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ProductsInfo]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<ProductsInfo>() {
        override fun areItemsTheSame(oldItem: ProductsInfo, newItem: ProductsInfo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductsInfo, newItem: ProductsInfo): Boolean {
            return oldItem.id == newItem.id
        }

    }

    /**
     * The [MyCartViewHolder] constructor takes the binding variable from the associated
     * layout_may_cart, which nicely gives it access to the full [ProductsInfo] information.
     */
    class MyCartViewHolder(private var binding: LayoutMyCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productsInfo: ProductsInfo){
            binding.property = productsInfo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(LayoutMyCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val productsInfo = getItem(position)
        holder.bind(productsInfo)
    }
}