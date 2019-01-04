package com.adambennett.jsonplaceholderapp.ui

import androidx.lifecycle.ViewModel
import com.adambennett.api.service.PlaceholderService
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class MainViewModel(
    private val placeholderService: PlaceholderService
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val viewStateSubject = BehaviorSubject.create<ViewState>()
    val inputEventSink = PublishSubject.create<UserIntent>()
    val viewStates: Observable<ViewState> = viewStateSubject

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}