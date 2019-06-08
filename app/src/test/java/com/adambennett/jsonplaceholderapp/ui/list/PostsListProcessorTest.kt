package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.testutils.rxjava.just
import com.adambennett.testutils.rxjava.rxInit
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.amshove.kluent.`it returns`
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should contain`
import org.junit.Rule
import org.junit.Test

class PostsListProcessorTest {

    @get:Rule
    val rxRule = rxInit { ioTrampoline() }

    @Test
    fun `returns result error when request fails`() {
        PostsAction.LoadPosts.just()
            .cast(PostsAction::class.java)
            .compose(
                PostsListProcessor(
                    mock {
                        on { getComments() } `it returns` Single.error(Throwable())
                        on { getPosts() } `it returns` Single.just(emptyList())
                        on { getUsers() } `it returns` Single.just(emptyList())
                    }
                )::apply
            )
            .test()
            .assertNoErrors()
            .values()
            .apply {
                this[0] `should be instance of` PostsResult.Loading::class.java
                this[1] `should be instance of` PostsResult.Error::class.java
            }
    }

    @Test
    fun `returns list as expected`() {
        PostsAction.LoadPosts.just()
            .cast(PostsAction::class.java)
            .compose(
                PostsListProcessor(
                    mock {
                        on { getComments() } `it returns` Single.just(
                            listOf(
                                Comment(
                                    postId = 1,
                                    id = 1,
                                    name = "name",
                                    email = "email",
                                    body = "body"
                                ),
                                Comment(
                                    postId = 1,
                                    id = 2,
                                    name = "name",
                                    email = "email",
                                    body = "body"
                                )
                            )
                        )
                        on { getPosts() } `it returns` Single.just(
                            listOf(
                                Post(
                                    title = "title",
                                    body = "body",
                                    userId = 7,
                                    id = 1
                                )
                            )
                        )
                        on { getUsers() } `it returns` Single.just(
                            listOf(
                                User(
                                    userName = "username",
                                    id = 7
                                )
                            )
                        )
                    }
                )::apply
            )
            .test()
            .assertNoErrors()
            .values()
            .apply {
                this[0] `should be instance of` PostsResult.Loading::class.java
                this[1] `should be instance of` PostsResult.Success::class.java
                val result = this[1] as PostsResult.Success

                result.data `should contain` ListDisplayModel(
                    title = "title",
                    username = "username",
                    commentCount = 2,
                    body = "body"
                )
            }
    }
}