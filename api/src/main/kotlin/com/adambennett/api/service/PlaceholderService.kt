package com.adambennett.api.service

import com.adambennett.api.api.PlaceholderEndpoints
import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User

class PlaceholderService internal constructor(
    private val placeholderEndpoints: PlaceholderEndpoints
) {

    // Mapping should be done here with injected Mapper classes

    suspend fun getComments(): List<Comment> = placeholderEndpoints.getComments()
        .map {
            Comment(
                id = it.id,
                postId = it.postId,
                email = it.email,
                name = it.name,
                body = it.body
            )
        }

    suspend fun getUsers(): List<User> = placeholderEndpoints.getUsers()
        .map {
            User(
                userName = it.username,
                id = it.id
            )
        }

    suspend fun getPosts(): List<Post> = placeholderEndpoints.getPosts()
        .map {
            Post(
                title = it.title,
                body = it.body,
                userId = it.userId,
                id = it.id
            )
        }
}
