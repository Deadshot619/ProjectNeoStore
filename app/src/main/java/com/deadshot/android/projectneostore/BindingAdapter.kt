package com.deadshot.android.projectneostore

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.deadshot.android.projectneostore.adapter.ProductsAdapter
import com.deadshot.android.projectneostore.models.ProductList
import com.deadshot.android.projectneostore.utils.LoadingProductsStatus

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

/**
 * This binding adapter displays the [LoadingProductsStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("loadingProductsStatus")
fun bindStatus(statusImageView: ImageView, status: LoadingProductsStatus?) {
    when (status) {
        LoadingProductsStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        LoadingProductsStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        LoadingProductsStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}