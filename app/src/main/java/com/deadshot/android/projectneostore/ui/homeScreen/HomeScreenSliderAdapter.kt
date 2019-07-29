package com.deadshot.android.projectneostore.ui.homeScreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.deadshot.android.projectneostore.R
import com.smarteist.autoimageslider.SliderViewAdapter

class HomeScreenSliderAdapter: SliderViewAdapter<HomeScreenSliderAdapter.HomeScreenSliderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?): HomeScreenSliderViewHolder {
        val view = LayoutInflater.from(parent?.context)
                                .inflate(R.layout.image_slider_layout_item, null)
        return HomeScreenSliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: HomeScreenSliderViewHolder?, position: Int) {
        when(position){
            0 -> viewHolder?.imageViewBackground?.setBackgroundResource(R.drawable.slider_img1)
            1 -> viewHolder?.imageViewBackground?.setBackgroundResource(R.drawable.slider_img2)
            2 -> viewHolder?.imageViewBackground?.setBackgroundResource(R.drawable.slider_img3)
            3 -> viewHolder?.imageViewBackground?.setBackgroundResource(R.drawable.slider_img4)
        }
    }

    override fun getCount(): Int = 4

    class HomeScreenSliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        val imageViewBackground = itemView?.findViewById(R.id.iv_auto_image_slider) as ImageView
    }
}