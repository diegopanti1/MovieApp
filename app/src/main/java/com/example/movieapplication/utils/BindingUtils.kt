package com.lumston.enone.utils

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Base64
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.movieapplication.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import java.util.*


/**
 * Created by Daniel Luna on 3/2/21
 */

@SuppressLint("CheckResult")
@BindingAdapter("loadImageUrl")
fun loadImage(view: ShapeableImageView, url: String?) {
    val requestOptions = RequestOptions()
    requestOptions.placeholder(R.drawable.ic_load)
    requestOptions.error(R.drawable.ic_failure)
    requestOptions.centerCrop()
    Glide
        .with(view.context)
        .setDefaultRequestOptions(requestOptions)
        .load(url)
        .into(view)
}



@SuppressLint("CheckResult")
@BindingAdapter("loadImageFromBase64")
fun loadImageFromBase64(view: ShapeableImageView, url: String?) {
    val decode = Base64.decode(url, Base64.DEFAULT)
    val bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.size)
    view.setImageBitmap(bitmap)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ShapeableImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("android:drawableStart")
fun setDrawableStartToTextView(view: MaterialTextView, resource: Int?) {
    view.setCompoundDrawablesWithIntrinsicBounds(
        if (resource != null) view.context.getDrawable(resource) else null,
        null,
        null,
        null
    )
}

@BindingAdapter("android:src")
fun setImageDrawable(
    view: ShapeableImageView,
    drawable: Drawable?
) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("errorText")
fun setErrorMessage(view: View, message: String?) {
    when (view) {
        is TextInputLayout -> {
            if (message != null)
                view.error = message
            else
                view.error = null
        }
        is MaterialCheckBox -> {
            if (message != null)
                view.error = message
            else
                view.error = null
        }
    }
}



fun getDayOfMonthSuffix(n: Int): String {
    if (n !in 1..31) throw IllegalArgumentException("Invalid day")
    if (n in 11..13) return "th"
    return when (n % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
}



@BindingAdapter("play_animation")
fun setLottieHappening(lottie: LottieAnimationView, isHappening: Boolean) {
    if (isHappening) lottie.playAnimation() else lottie.pauseAnimation()
}

@BindingAdapter("textProfile")
fun setTextProfile(view: MaterialTextView, text: String?) {
    text.let {
        view.text = if (text.isNullOrEmpty()) "No information" else text
    }
}

