package com.adambennett.jsonplaceholderapp.ui.list

/**
 * These represent the current state of the View and all the data required for it to render
 */
sealed class ViewState

object Loading : ViewState()
data class Error(val errorMessage: String) : ViewState()
data class Data(val posts: List<ListDisplayModel>) : ViewState()

/**
 * These intents represent user actions to be interpreted into logical actions
 */
sealed class UserIntent

object RefreshIntent : UserIntent()

/**
 * These classes represent logical actions to be undertaken by the processor
 */
sealed class Action

object LoadPostsAction : Action()
object UnregistedAction : Action()

/**
 * These classes represent results from the processor - generally errors or data
 */
sealed class Result

object ResultLoading : Result()
data class ResultData<T>(val data: List<T>) : Result()
data class ResultError(val errorMessage: String) : Result()