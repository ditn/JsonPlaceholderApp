package com.adambennett.jsonplaceholderapp.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Allows a [ViewGroup] to inflate itself without all of the unneeded ceremony of getting a
 * [LayoutInflater] and always passing the [ViewGroup] + false. True can optionally be passed if
 * needed.
 *
 * @param layoutId The layout ID as an [Int]
 * @return The inflated [View]
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)