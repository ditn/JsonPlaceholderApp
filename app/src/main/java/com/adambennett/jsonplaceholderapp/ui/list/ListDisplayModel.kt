package com.adambennett.jsonplaceholderapp.ui.list

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ListDisplayModel(
    val title: String,
    val body: String,
    val commentCount: Int,
    val username: String
) : Parcelable
