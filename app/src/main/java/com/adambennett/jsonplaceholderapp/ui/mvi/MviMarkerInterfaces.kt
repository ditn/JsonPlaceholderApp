package com.adambennett.jsonplaceholderapp.ui.mvi

/**
 * Intents represent intents from the user - whether opening the screen, clicking a button,
 * entering text, reaching the bottom of a list etc.
 *
 * Intents are translated into their respective logical [MviAction]. For instance, "opening a page"
 * intent might translate to "invalidate the cache and load fresh data". The intent and action
 * are often similar but this is important to avoid the data flow being too tightly coupled to
 * the UI.
 */
interface MviIntent<A : MviAction> {

    /**
     * Maps an [MviIntent] to it's associated [MviAction].
     */
    fun mapToAction(): A
}

/**
 * Actions define the logic that should be executed by the Processor.
 *
 * The processor executes Actions. Inside a [MviViewModel], this is the only place where
 * side effects should happen, ie data writing, reading etc.
 */
interface MviAction

/**
 * Results are the result of Actions having been executed by the processor. These can be errors,
 * successful execution, request in flight etc.
 */
interface MviResult

/**
 * The ViewState contains all of the information that the View needs to render itself.
 */
interface MviViewState


/**
 * The Reducer is responsible for generating the [MviViewState] which the View will use to render
 * itself. The View should be stateless in the sense that the [MviViewState] should be sufficient
 * for the rendering. The [Reducer] takes the latest [MviViewState] available, apply the
 * latest [MviResult] to it and return a whole new [MviViewState].
 */
typealias Reducer<S, R> = (state: S, result: R) -> S