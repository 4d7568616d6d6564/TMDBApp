package com.mkdevelopment.tmdbapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mkdevelopment.tmdbapp.R


val options = RequestOptions()
    .centerCrop()
    .placeholder(R.drawable.ic_star)
    .error(R.drawable.ic_star)

fun ImageView.loadCircleImage(path: String?) {
    Glide.with(this.context).load(path)
        .centerCrop()
        .apply(options)
        .centerCrop()
        .into(this)
}

fun ImageView.loadImage(path: String?) {
    Glide.with(this.context).load(path)
        .centerCrop()
        .apply(options)
        .into(this)
}