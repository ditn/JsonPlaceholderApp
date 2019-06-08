package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsViewState
import com.adambennett.jsonplaceholderapp.ui.list.models.UserIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.BaseMviViewModel
import com.adambennett.jsonplaceholderapp.ui.mvi.MviAction
import com.adambennett.jsonplaceholderapp.ui.mvi.MviIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.MviResult
import com.adambennett.jsonplaceholderapp.ui.mvi.MviViewState
import com.adambennett.jsonplaceholderapp.ui.mvi.Reducer
import com.adambennett.jsonplaceholderapp.ui.mvi.ViewStateEvent

class ListModel(
    processor: PostsListProcessor
) : BaseMviViewModel<UserIntent, PostsAction, PostsResult, PostsViewState>(
    processor,
    PostsViewState(),
    PostsViewState.reducer
)