package com.adambennett.jsonplaceholderapp.ui.list

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

class ListModel(
    private val processor: PostsListProcessor
) : ViewModel(), MviViewModel {

    private val inputEventSink = PublishSubject.create<UserIntent>()
    private val viewStateSubject = compose()

    override fun processIntents(intents: Observable<UserIntent>) {
        intents.subscribe(inputEventSink)
    }

    private fun compose(): Observable<ViewState> =
        inputEventSink
            .map(UserIntent::toAction)
            .compose(processor.actionProcessor)
            .map(Result::toViewState)
            .observeOn(AndroidSchedulers.mainThread())
            .distinctUntilChanged()
            // Publish the last state on reconnection. Useful when a View rebinds to the ViewModel
            // after the device rotates.
            .replay(1)
            // Create the stream without requiring anyone to subscribe to it. This keeps the stream
            // alive when the UI disconnects.
            .autoConnect(0)

    override fun states(): Observable<ViewState> = viewStateSubject
}

/**
 * Realistically for a more complex app, [ViewState] would be an interface and [Data] would be a
 * data class. In this instance, here we'd use a [Observable.scan] operator and mutate the
 * data class (ie copy and change fields - it's not actually mutable) based on the previous state.
 * However here we don't need to as the app can be modeled easily just using sealed classes.
 */
private fun Result.toViewState(): ViewState = when (this) {
    ResultLoading -> Loading
    is ResultData<*> -> Data(this.data as List<ListDisplayModel>)
    is ResultError -> Error(errorMessage)
}

private fun UserIntent.toAction(): Action = when (this) {
    RefreshIntent -> LoadPostsAction
}