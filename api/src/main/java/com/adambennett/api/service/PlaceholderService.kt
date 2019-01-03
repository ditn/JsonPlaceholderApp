package com.adambennett.api.service

import com.adambennett.api.api.PlaceholderEndpoints
import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User
import io.reactivex.Single

class PlaceholderService internal constructor(
    private val placeholderEndpoints: PlaceholderEndpoints
) {

    // Mapping could be done here with injected Mapper classes if we were going full Clean Code

    fun getComments(): Single<List<Comment>> = placeholderEndpoints.getComments()
        .map { list -> list.map { Comment(id = it.id) } }

    fun getUsers(): Single<List<User>> = placeholderEndpoints.getUsers()
        .map { list ->
            list.map {
                User(
                    name = it.name,
                    id = it.id
                )
            }
        }

    fun getPosts(): Single<List<Post>> = placeholderEndpoints.getPosts()
        .map { list ->
            list.map {
                Post(
                    title = it.title,
                    body = it.body,
                    userId = it.userId
                )
            }
        }
}