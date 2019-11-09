package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.testutils.rxjava.just
import com.adambennett.testutils.rxjava.rxInit
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Single
import org.amshove.kluent.itReturns
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContain
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
                        on { getComments() } itReturns Single.error(Throwable())
                        on { getPosts() } itReturns Single.just(emptyList())
                        on { getUsers() } itReturns Single.just(emptyList())
                    }
                )::apply
            )
            .test()
            .assertNoErrors()
            .values()
            .apply {
                this[0] shouldBeInstanceOf PostsResult.Loading::class.java
                this[1] shouldBeInstanceOf PostsResult.Error::class.java
            }
    }

    @Test
    fun `returns list as expected`() {
        PostsAction.LoadPosts.just()
            .cast(PostsAction::class.java)
            .compose(
                PostsListProcessor(
                    mock {
                        on { getComments() } itReturns Single.just(
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
                        on { getPosts() } itReturns Single.just(
                            listOf(
                                Post(
                                    title = "title",
                                    body = "body",
                                    userId = 7,
                                    id = 1
                                )
                            )
                        )
                        on { getUsers() } itReturns Single.just(
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
                this[0] shouldBeInstanceOf PostsResult.Loading::class.java
                this[1] shouldBeInstanceOf PostsResult.Success::class.java
                val result = this[1] as PostsResult.Success

                result.data shouldContain ListDisplayModel(
                    title = "title",
                    username = "username",
                    commentCount = 2,
                    body = "body"
                )
            }
    }
}
