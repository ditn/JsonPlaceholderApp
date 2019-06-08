package com.adambennett.jsonplaceholderapp.ui.list.models

import com.adambennett.jsonplaceholderapp.ui.list.ListDisplayModel
import com.adambennett.jsonplaceholderapp.ui.mvi.MviAction
import com.adambennett.jsonplaceholderapp.ui.mvi.MviIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.MviResult
import com.adambennett.jsonplaceholderapp.ui.mvi.MviViewState
import com.adambennett.jsonplaceholderapp.ui.mvi.Reducer
import com.adambennett.jsonplaceholderapp.ui.mvi.ViewStateEvent

sealed class UserIntent : MviIntent<PostsAction> {

    object Refresh : UserIntent()

    override fun mapToAction(): PostsAction = when (this) {
        Refresh -> PostsAction.LoadPosts
    }
}

sealed class PostsAction : MviAction {

    object LoadPosts : PostsAction()
}

sealed class PostsResult : MviResult {

    object Loading : PostsResult()
    data class Error(val errorMessage: String) : PostsResult()
    data class Success(val data: List<ListDisplayModel>) : PostsResult()
}

data class PostsViewState(
    val refreshing: Boolean = false,
    val data: List<ListDisplayModel> = emptyList(),
    val error: ViewStateEvent<String>? = null
) : MviViewState {

    companion object {

        val reducer: Reducer<PostsViewState, PostsResult> = { state, result ->
            when (result) {
                PostsResult.Loading -> state.copy(refreshing = true, error = null)
                is PostsResult.Error -> state.copy(
                    refreshing = false,
                    error = ViewStateEvent(result.errorMessage)
                )
                is PostsResult.Success -> state.copy(
                    refreshing = false,
                    error = null,
                    data = result.data
                )
            }
        }
    }
}