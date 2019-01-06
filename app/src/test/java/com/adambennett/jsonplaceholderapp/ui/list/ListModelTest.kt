package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.jsonplaceholderapp.testutils.rxInitAndroid
import com.adambennett.jsonplaceholderapp.ui.mvi.Data
import com.adambennett.jsonplaceholderapp.ui.mvi.Error
import com.adambennett.jsonplaceholderapp.ui.mvi.Loading
import com.adambennett.jsonplaceholderapp.ui.mvi.RefreshIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultData
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultError
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultLoading
import com.adambennett.testutils.rxjava.just
import com.nhaarman.mockito_kotlin.mock
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
        ListModel(
            mock {
                on { actionProcessor } `it returns` ObservableTransformer {
                    ResultData(listOf(ListDisplayModel("", "", 1, ""))).just()
                }
            }
        ).apply {
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
        ListModel(
            mock {
                on { actionProcessor } `it returns` ObservableTransformer {
                    ResultLoading.just()
                }
            }
        ).apply {
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
        ListModel(
            mock {
                on { actionProcessor } `it returns` ObservableTransformer {
                    ResultError("").just()
                }
            }
        ).apply {
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