package com.adambennett.jsonplaceholderapp.ui

/**
 * These represent the current state of the View
 */
sealed class ViewState

object Loading : ViewState()
data class Error(val errorMessage: String) : ViewState()
data class Data(val comments: List<String>) : ViewState()

/**
 * These intents represent user actions
 */
sealed class UserIntent

object RefreshIntent : UserIntent()
// TODO: Pass user ID
object SelectIntent : UserIntent()