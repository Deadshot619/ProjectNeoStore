package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.R
import com.deadshot.android.projectneostore.databinding.LayoutMyCartItemBinding
import com.deadshot.android.projectneostore.models.ProductsInfo
import com.deadshot.android.projectneostore.models.SingleProductInfo

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 *
 * @param clickDeleteListener : reference to a clickListener for data binding to know where to call onCLick.
 * (- Our goal is to get this click event out of the fragment, so we won't define the clickListener here.
 *  - Instead we'll just let the fragment pass a listener to us.
 *  - This way our adapter doesn't care about how clicks get handled, it just takes a callback)
 */
class MyCartAdapter(val clickDeleteListener: OnClickDeleteListener) : ListAdapter<ProductsInfo, MyCartAdapter.MyCartViewHolder>(DiffCallback){

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [SingleProductInfo]
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
     * layout_may_cart, which nicely gives it access to the full [SingleProductInfo] information.
     */
    class MyCartViewHolder(private var binding: LayoutMyCartItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            productsInfo: ProductsInfo,
            clickDeleteListener: OnClickDeleteListener
        ){
            binding.property = productsInfo
            /**
             * Set spinner items for each viewHolder
             */
            binding.spinnerQuantity.adapter = ArrayAdapter.createFromResource(
                binding.root.context ,
                R.array.items ,
                android.R.layout.simple_list_item_1
            )
            //set selected quantity in spinner from db
            binding.spinnerQuantity.setSelection(productsInfo.quantity - 1)
            binding.clickListener = clickDeleteListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCartViewHolder {
        return MyCartViewHolder(LayoutMyCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyCartViewHolder, position: Int) {
        val productsInfo= getItem(position)
        //tell databinding about our clickListener, pass clickListener to viewholder
        holder.bind(productsInfo, clickDeleteListener)
    }

    class OnClickDeleteListener(val clickListener: (productId: Int) -> Unit){
        fun onClick(productsInfo: ProductsInfo) = clickListener(productsInfo.productId)
    }
}