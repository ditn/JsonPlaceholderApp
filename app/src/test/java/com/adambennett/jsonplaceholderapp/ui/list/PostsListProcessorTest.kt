package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.testutils.rxjava.just
import com.adambennett.testutils.rxjava.rxInit
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import org.amshove.kluent.`it returns`
import org.junit.Rule
import org.junit.Test

class PostsListProcessorTest {

    @get:Rule
    val rxRule = rxInit { ioTrampoline() }

    @Test
    fun `throws with unregistered action type`() {
        UnregistedAction.just()
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
                this[0] is ResultLoading
                this[0] is ResultError
            }
    }
}