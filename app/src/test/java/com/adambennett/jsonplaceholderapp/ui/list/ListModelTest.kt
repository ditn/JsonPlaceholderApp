package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.jsonplaceholderapp.testutils.rxInitAndroid
import com.adambennett.testutils.rxjava.just
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.amshove.kluent.`it returns`
import org.amshove.kluent.`should be instance of`
import org.junit.Rule
import org.junit.Test

class ListModelTest {

    @get:Rule
    val rxRule = rxInitAndroid { mainTrampoline() }

    @Test
    fun `accepts loading event, returns data, mapped to ViewState`() {
        ListModel(mock {
            on { actionProcessor } `it returns` ObservableTransformer {
                Observable.just(
                    ResultData(
                        listOf(ListDisplayModel("", "", 1, ""))
                    )
                )
            }
        }).apply {
            processIntents(RefreshIntent.just())
            states()
                .test()
                .values()
                .apply {
                    this[0] `should be instance of` Data::class.java
                }
        }
    }

    @Test
    fun `accepts loading event, returns loading, mapped to ViewState`() {
        ListModel(mock {
            on { actionProcessor } `it returns` ObservableTransformer {
                Observable.just(ResultLoading)
            }
        }).apply {
            processIntents(RefreshIntent.just())
            states()
                .test()
                .values()
                .apply {
                    this[0] `should be instance of` Loading::class.java
                }
        }
    }

    @Test
    fun `accepts loading event, returns error, mapped to ViewState`() {
        ListModel(mock {
            on { actionProcessor } `it returns` ObservableTransformer {
                Observable.just(ResultError(""))
            }
        }).apply {
            processIntents(RefreshIntent.just())
            states()
                .test()
                .values()
                .apply {
                    this[0] `should be instance of` Error::class.java
                }
        }
    }
}