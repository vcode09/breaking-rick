package com.vmoreno.butterfly.rick.modules.character.bindingAdapter

import android.view.View
import android.view.View.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.vmoreno.butterfly.rick.R
import com.vmoreno.butterfly.rick.common.ScopedViewModel
import com.vmoreno.butterfly.rick.modules.character.entities.CharacterBreakingBadUi
import com.vmoreno.butterfly.rick.modules.character.entities.EpisodesBreakingBadUi
import com.vmoreno.butterfly.rick.utils.hideKeyboard
import com.vmoreno.butterfly.rick.view.imageLoadingCaching.ImageLoader
import com.google.android.material.imageview.ShapeableImageView

@BindingAdapter("loadImageUrl")
fun ShapeableImageView.loadImage(url: String?) {
    url?.let { ImageLoader(context).loadImage(url, this) }
}

@BindingAdapter("isFavourite")
fun ImageView.isFavourite(character: CharacterBreakingBadUi?) {
    character?.let {
        if (it.isFavourite) {
            setImageDrawable(
                ContextCompat.getDrawable(
                    context, // Context
                    R.drawable.ic_favourite_fill// Drawable
                )
            )
        } else {
            setImageDrawable(
                ContextCompat.getDrawable(
                    context, // Context
                    R.drawable.ic_favourite_empty// Drawable
                )
            )
        }
    }
}

@BindingAdapter("isFavouriteLottie")
fun LottieAnimationView.isFavouriteLottie(isFavourite: Boolean) {
    visibility = if (isFavourite) VISIBLE
    else INVISIBLE
}

@BindingAdapter("uiLoading")
fun View.uiLoading(uiState: ScopedViewModel.UiModel?) {
    visibility = if (uiState is ScopedViewModel.UiModel.Loading) VISIBLE
    else GONE
}

@BindingAdapter("uiErrorState")
fun View.uiErrorState(uiState: ScopedViewModel.UiModel?) {
    this.hideKeyboard()
    visibility = if (uiState is ScopedViewModel.UiModel.ErrorState) VISIBLE
    else GONE
}

@BindingAdapter("uiNoInternetState")
fun View.uiNoInternetState(uiState: ScopedViewModel.UiModel?) {
    this.hideKeyboard()
    visibility = if (uiState is ScopedViewModel.UiModel.NoInternetState) VISIBLE
    else GONE
}

@BindingAdapter("uiEmptyState")
fun View.uiEmptyState(uiState: ScopedViewModel.UiModel?) {
    this.hideKeyboard()
    visibility = if (uiState is ScopedViewModel.UiModel.EmptyState) VISIBLE
    else GONE
}

@BindingAdapter("uiEpisodesView")
fun View.uiEpisodesView(episodesBreakingBadUi: EpisodesBreakingBadUi) {
    this.hideKeyboard()
    visibility = if (episodesBreakingBadUi.episodeId != -1) VISIBLE
    else GONE
}
