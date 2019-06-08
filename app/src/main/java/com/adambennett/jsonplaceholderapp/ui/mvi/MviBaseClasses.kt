package com.adambennett.jsonplaceholderapp.ui.mvi

import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

interface MviViewModel<I : MviIntent<A>, A : MviAction, S : MviViewState> {

    val states: Observable<S>

    fun processIntents(intents: Observable<I>)
}

interface MviView<I : MviIntent<A>, A : MviAction, S : MviViewState> {

    val intents: Observable<I>

    fun render(state: S)
}

open class BaseMviViewModel<I : MviIntent<A>, A : MviAction, R : MviResult, S : MviViewState>(
    private val processor: MviActionProcessor<A, R>,
    initialState: S,
    reducer: Reducer<S, R>
) : MviViewModel<I, A, S>, ViewModel() {

    // Proxy subject that keeps the stream alive during config changes
    private val intentSink: PublishSubject<I> = PublishSubject.create()

    final override val states: Observable<S> = intentSink
        .map { it.mapToAction() }
        .compose { processor.apply(it) }
        .scan(initialState, reducer)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        // Replay last event on reconnection, ie allow a View to get the last state
        // on rebinding after a config change.
        .replay(1)
        // Create the stream immediately without waiting for subscribers. Stream stays
        // alive even when the UI disconnects.
        .autoConnect()

    final override fun processIntents(intents: Observable<I>) = intents.subscribe(intentSink)

    // Internal state handling
    private val internalDisposable = states.subscribe(::internalLogger, ::crashHandler)

    // Log all ViewStates
    private fun internalLogger(state: S) = Timber.i("$state")

    // Raise any unhandled exceptions
    private fun crashHandler(throwable: Throwable): Unit = throw throwable
}