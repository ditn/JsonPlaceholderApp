package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsViewState
import com.adambennett.jsonplaceholderapp.ui.list.models.UserIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.BaseMviViewModel

class ListModel(
    processor: PostsListProcessor
) : BaseMviViewModel<UserIntent, PostsAction, PostsResult, PostsViewState>(
    processor,
    UserIntent.InitialIntent::class.java,
    PostsViewState(),
    PostsViewState.reducer
)
