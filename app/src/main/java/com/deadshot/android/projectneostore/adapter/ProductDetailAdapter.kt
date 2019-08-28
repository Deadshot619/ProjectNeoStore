package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.databinding.LayoutProductImageBinding
import com.deadshot.android.projectneostore.models.ProductImage

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class ProductDetailAdapter(private val onClickListener: OnClickListener): ListAdapter<ProductImage, ProductDetailAdapter.ProductDetailViewHolder>(DiffCallback){
    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ProductImage]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<ProductImage>() {
        override fun areItemsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ProductImage, newItem: ProductImage): Boolean {
            return oldItem.id == newItem.id
        }

    }

    /**
     * The ProductDetailViewHolder constructor takes the binding variable from the associated
     * Product__image_layout, which nicely gives it access to the full [ProductImage] information.
     */
    class ProductDetailViewHolder(private var binding: LayoutProductImageBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(productImage: ProductImage){
            binding.property = productImage
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductDetailViewHolder {
        return ProductDetailViewHolder(LayoutProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {
        val productImage = getItem(position)
        holder.bind(productImage)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(productImage)
        }
    }

    class OnClickListener(val clickListener: (productImage: ProductImage) -> Unit){
        fun onClick(productImage: ProductImage) = clickListener(productImage)
    }

}