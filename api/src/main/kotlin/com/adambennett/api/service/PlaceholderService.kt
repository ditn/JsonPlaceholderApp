package com.adambennett.api.service

import com.adambennett.api.api.PlaceholderEndpoints
import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User
import io.reactivex.Single

class PlaceholderService internal constructor(
    private val placeholderEndpoints: PlaceholderEndpoints
) {

    // Mapping could be done here with injected Mapper classes

    fun getComments(): Single<List<Comment>> = placeholderEndpoints.getComments()
        .map { list ->
            list.map {
                Comment(
                    id = it.id,
                    postId = it.postId,
                    email = it.email,
                    name = it.name,
                    body = it.body
                )
            }
        }

    fun getUsers(): Single<List<User>> = placeholderEndpoints.getUsers()
        .map { list ->
            list.map {
                User(
                    userName = it.username,
                    userId = it.id
                )
            }
        }

    fun getPosts(): Single<List<Post>> = placeholderEndpoints.getPosts()
        .map { list ->
            list.map {
                Post(
                    title = it.title,
                    body = it.body,
                    userId = it.userId,
                    id = it.id
                )
            }
        }
}