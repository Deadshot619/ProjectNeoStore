package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.databinding.LayoutMyOrdersBinding
import com.deadshot.android.projectneostore.models.OrderList

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class OrderListAdapter(private val onClickListener: OnClickListener):
    ListAdapter<OrderList , OrderListAdapter.OrderListViewHolder>(DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): OrderListViewHolder {
        return OrderListViewHolder(LayoutMyOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OrderListViewHolder , position: Int) {
        val orderList = getItem(position)
        holder.bind(orderList, onClickListener)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [OrderList]
     * has been updated.
     */
    companion object DiffCallback: DiffUtil.ItemCallback<OrderList>(){
        override fun areItemsTheSame(oldItem: OrderList , newItem: OrderList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: OrderList , newItem: OrderList): Boolean {
            return oldItem.id == newItem.id
        }

    }

    /**
     * The [OrderListViewHolder] constructor takes the binding variable from the associated
     * layout_may_cart, which nicely gives it access to the full [OrderList] information.
     */
    class OrderListViewHolder(private var binding: LayoutMyOrdersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            orderList: OrderList,
            onClickListener: OnClickListener
        ){
            binding.orderList = orderList
            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (orderList: OrderList) -> Unit){
        fun onClick(orderList: OrderList) = clickListener(orderList)
    }

}