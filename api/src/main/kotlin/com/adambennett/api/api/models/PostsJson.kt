package com.adambennett.api.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostsJson(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)
