package com.adambennett.jsonplaceholderapp.ui.mvi

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

abstract class MviActionProcessor<A : MviAction, R : MviResult> : ObservableTransformer<A, R> {

    final override fun apply(actions: Observable<A>): ObservableSource<R> =
        actions.publish { shared -> Observable.merge(getActionProcessors(shared)) }

    abstract fun getActionProcessors(shared: Observable<A>): List<Observable<R>>

    inline fun <reified A> Observable<in A>.connect(
        processor: ObservableTransformer<A, R>
    ): Observable<R> = ofType(A::class.java).compose(processor)
}