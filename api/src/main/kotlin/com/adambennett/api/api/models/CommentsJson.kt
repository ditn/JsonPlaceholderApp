package com.adambennett.api.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class CommentsJson(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
