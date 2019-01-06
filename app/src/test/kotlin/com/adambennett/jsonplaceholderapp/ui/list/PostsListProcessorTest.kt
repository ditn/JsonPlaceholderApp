package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.api.service.models.Comment
import com.adambennett.api.service.models.Post
import com.adambennett.api.service.models.User
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
    fun `throws with unregistered action type`() {
        UnregistedAction.just()
            .cast(Action::class.java)
            .compose(
                PostsListProcessor(
                    mock {
                        on { getComments() } `it returns` Single.never()
                        on { getPosts() } `it returns` Single.never()
                        on { getUsers() } `it returns` Single.never()
                    }
                ).actionProcessor
            )
            .test()
            .assertError(IllegalArgumentException::class.java)
    }

    @Test
    fun `returns result error when request fails`() {
        LoadPostsAction.just()
            .cast(Action::class.java)
            .compose(
                PostsListProcessor(
                    mock {
                        on { getComments() } `it returns` Single.error(Throwable())
                        on { getPosts() } `it returns` Single.just(emptyList())
                        on { getUsers() } `it returns` Single.just(emptyList())
                    }
                ).actionProcessor
            )
            .test()
            .assertNoErrors()
            .values()
            .apply {
                this[0] `should be instance of` ResultLoading::class.java
                this[1] `should be instance of` ResultError::class.java
            }
    }

    @Test
    fun `returns list as expected`() {
        LoadPostsAction.just()
            .cast(Action::class.java)
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
                                    userId = 7
                                )
                            )
                        )
                    }
                ).actionProcessor
            )
            .test()
            .assertNoErrors()
            .values()
            .apply {
                this[0] `should be instance of` ResultLoading::class.java
                this[1] `should be instance of` ResultData::class.java
                val result = this[1] as ResultData<List<ListDisplayModel>>

                result.data `should contain` ListDisplayModel(
                    title = "title",
                    username = "username",
                    commentCount = 2,
                    body = "body"
                )
            }
    }
}