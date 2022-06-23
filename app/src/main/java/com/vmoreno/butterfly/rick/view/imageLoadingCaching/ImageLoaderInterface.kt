package com.vmoreno.butterfly.rick.view.imageLoadingCaching

import com.google.android.material.imageview.ShapeableImageView

interface LoadImageInterface<L> {

    fun loadImage(uri: String, target: ShapeableImageView)

    fun loadImage(uri: String, target: ShapeableImageView, listener: L)
}