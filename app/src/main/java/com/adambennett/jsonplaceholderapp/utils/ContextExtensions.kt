package com.adambennett.jsonplaceholderapp.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Context.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    context?.toast(message, length)
}

fun Fragment.toast(@StringRes message: Int, length: Int = Toast.LENGTH_SHORT) {
    context?.toast(message, length)
}