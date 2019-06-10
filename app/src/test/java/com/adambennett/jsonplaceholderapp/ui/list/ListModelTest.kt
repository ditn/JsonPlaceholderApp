package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.jsonplaceholderapp.testutils.rxInitAndroid
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsViewState
import com.adambennett.jsonplaceholderapp.ui.list.models.UserIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.ViewStateEvent
import com.adambennett.testutils.rxjava.just
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import org.amshove.kluent.`it returns`
import org.amshove.kluent.`should equal`
import org.junit.Rule
import org.junit.Test

class ListModelTest {

    @get:Rule
    val rxRule = rxInitAndroid {
        mainTrampoline()
        ioTrampoline()
    }

    @Test
    fun `accepts loading event, returns data, mapped to ViewState`() {
        ListModel(
            mock {
                on { apply(any()) } `it returns`
                    PostsResult.Success(listOf(ListDisplayModel("", "", 1, ""))).just<PostsResult>()
            }
        ).apply {
            processIntents(UserIntent.Refresh.just())
            states
                .test()
                .values()
                .first()
                .apply {
                    this `should equal` PostsViewState(
                        data = listOf(
                            ListDisplayModel(
                                "",
                                "",
                                1,
                                ""
                            )
                        )
                    )
                }
        }
    }

    @Test
    fun `accepts loading event, returns loading, mapped to ViewState`() {
        ListModel(
            mock {
                on { apply(any()) } `it returns`
                    PostsResult.Loading.just<PostsResult>()
            }
        ).apply {
            processIntents(UserIntent.Refresh.just())
            states
                .test()
                .values()
                .first()
                .apply {
                    this `should equal` PostsViewState(refreshing = true)
                }
        }
    }

    @Test
    fun `accepts loading event, returns error, mapped to ViewState`() {
        ListModel(
            mock {
                on { apply(any()) } `it returns` PostsResult.Error("").just<PostsResult>()
            }
        ).apply {
            processIntents(UserIntent.Refresh.just())
            states
                .test()
                .values()
                .first()
                .apply {
                    this `should equal` PostsViewState(error = ViewStateEvent(""))
                }
        }
    }
}