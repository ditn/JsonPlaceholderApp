package com.adambennett.jsonplaceholderapp.ui.mvi

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Used to represent a one-shot UI event within an [MviViewState], so that we don't have to
 * toggle [Boolean] values or use timers in Rx or anything too wild. [consume] allows you to
 * process the event only once.
 *
 * Can store whatever data you might want - most of the time this would be a [String] or
 * res ID [Int].
 */
data class ViewStateEvent<T>(val payload: T? = null) {

    val isConsumed = AtomicBoolean(false)

    /**
     * Allows you to handle the [payload] of the [ViewStateEvent] without marking the event as
     * consumed.
     */
    fun handle(action: (T?) -> Unit) {
        if (isConsumed.get()) {
            action(payload)
        }
    }

    /**
     * Allows you to consume the [payload] of the event only once, as it will be marked as
     * consumed on access.
     */
    fun consume(action: (T?) -> Unit) {
        if (isConsumed.getAndSet(true)) {
            action(payload)
        }
    }
}

/**
 * For one shot events that don't contain any extra data - perhaps a generic error. [Nothing] in
 * Kotlin is used to indicate the absence of a type when using generics.
 */
typealias EmptyViewStateEvent = ViewStateEvent<Nothing>
