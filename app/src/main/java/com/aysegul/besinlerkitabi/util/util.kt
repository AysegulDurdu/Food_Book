package com.aysegul.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aysegul.besinlerkitabi.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.gorselIndir(url: String?, placeholder : CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher_round)


    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

}
fun placeHolderYap(context: Context) : CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}

@BindingAdapter("android:dowloadImage")
fun downloadImage(view : ImageView, url : String){
    view.gorselIndir(url, placeHolderYap(view.context))
}