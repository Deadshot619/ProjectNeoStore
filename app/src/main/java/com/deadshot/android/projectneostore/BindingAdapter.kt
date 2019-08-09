package com.deadshot.android.projectneostore

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.deadshot.android.projectneostore.Adapter.ProductsAdapter
import com.deadshot.android.projectneostore.models.ProductList

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imgUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("http").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image_black_24dp))
            .into(imgView)
    }
}

/**
* When there is no Products List data (data is null), hide the [RecyclerView], otherwise show it.
*/
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ProductList>?){
    val adapter = recyclerView.adapter as ProductsAdapter
    adapter.submitList(data)
}