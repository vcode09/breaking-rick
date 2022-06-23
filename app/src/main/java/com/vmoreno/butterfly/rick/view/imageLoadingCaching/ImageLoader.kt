package com.vmoreno.butterfly.rick.view.imageLoadingCaching

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.vmoreno.butterfly.rick.R
import com.google.android.material.imageview.ShapeableImageView

class ImageLoader(
    private val context: Context
) : LoadImageInterface<RequestListener<Drawable>> {

    private val options by lazy {
        RequestOptions()
            .dontTransform()
            .placeholder(R.drawable.ic_default_picture)
            .error(R.drawable.ic_not_found_picture)
    }

    override fun loadImage(uri: String, target: ShapeableImageView) {
        Glide.with(context)
            .load(uri)
            .apply(options)
            .into(target)
    }

    override fun loadImage(uri: String, target: ShapeableImageView, listener: RequestListener<Drawable>) {
        Glide.with(context)
            .load(uri)
            .apply(options)
            .listener(listener)
            .into(target)
    }
}