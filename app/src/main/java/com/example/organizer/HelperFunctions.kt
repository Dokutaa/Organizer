package com.example.organizer

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun loadImage(img: String, view: ImageView) {
    Picasso.get().load(img).into(view)
}