package com.deadshot.android.projectneostore.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.deadshot.android.projectneostore.database.Address
import com.deadshot.android.projectneostore.databinding.LayoutAddressBinding

class AddressListAdapter(val onClickListener: OnClickListener) : ListAdapter<Address, AddressListAdapter.ViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder {
        return ViewHolder(LayoutAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int) {
        val address = getItem(position)
        holder.bind(address, onClickListener)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Address>(){
        override fun areItemsTheSame(oldItem: Address , newItem: Address): Boolean {
            return oldItem.addressId == newItem.addressId
        }

        override fun areContentsTheSame(oldItem: Address , newItem: Address): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(val binding: LayoutAddressBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Address,
            onClickListener: OnClickListener
        ){
            binding.address = item
            binding.clickListener = onClickListener
            binding.executePendingBindings()
        }
    }

    class OnClickListener(val clickListener: (address: Address) -> Unit){
        fun onClick(address: Address) = clickListener(address)
    }
}