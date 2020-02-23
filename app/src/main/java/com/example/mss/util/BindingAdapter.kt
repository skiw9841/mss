package com.example.mss.util

import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, profileUrl: String?) {

    if(!TextUtils.isEmpty(profileUrl)) {
        Glide.with(view.context)
            .load(profileUrl)
            .into(view)
    }
}

