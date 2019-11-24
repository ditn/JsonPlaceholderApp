package com.adambennett.api.api

import com.adambennett.api.api.models.CommentsJson
import com.adambennett.api.api.models.PostsJson
import com.adambennett.api.api.models.UsersJson
import retrofit2.http.GET

internal const val PATH_COMMENTS = "comments"
internal const val PATH_POSTS = "posts"
internal const val PATH_USERS = "users"

internal interface PlaceholderEndpoints {

    @GET(PATH_COMMENTS)
    suspend fun getComments(): List<CommentsJson>

    @GET(PATH_POSTS)
    suspend fun getPosts(): List<PostsJson>

    @GET(PATH_USERS)
    suspend fun getUsers(): List<UsersJson>
}
