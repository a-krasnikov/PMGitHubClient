package krasnikov.project.pmgithubclient.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.load(url: String) {
    Glide.with(this).load(url).circleCrop().into(this)
}
