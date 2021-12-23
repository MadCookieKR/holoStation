package com.madcookie.holostation.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("loadImage")
fun ImageView.setDrawable(drawable : Drawable){
    loadImage(drawable)
}

private fun ImageView.loadImage(drawable: Drawable) {
    Glide.with(this)
        .load(drawable)
        .into(this)
}

