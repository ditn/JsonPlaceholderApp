package com.adambennett.jsonplaceholderapp.utils

import android.app.Activity

inline fun <reified T : Any> Activity.extra(key: String, default: T? = null): Lazy<T?> =
    unsafeLazy {
        val value = intent?.extras?.get(key)
        if (value is T) value else default
    }

inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null): Lazy<T> =
    unsafeLazy {
        val value = intent?.extras?.get(key)
        requireNotNull(if (value is T) value else default) { key }
    }
