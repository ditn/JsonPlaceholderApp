package com.adambennett.jsonplaceholderapp.ui.list

import io.reactivex.Observable

internal interface MviView {

    fun intents(): Observable<UserIntent>

    fun render(state: ViewState)
}

internal interface MviViewModel {

    fun processIntents(intents: Observable<UserIntent>)

    fun states(): Observable<ViewState>
}