package com.adambennett.jsonplaceholderapp.ui.list

import androidx.lifecycle.ViewModel
import com.adambennett.api.service.PlaceholderService
import com.adambennett.api.service.models.User
import com.adambennett.jsonplaceholderapp.IncrementingMap
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class ListViewModel(
    private val processor: PostsListProcessor
) : ViewModel(), MviViewModel {

    private val viewStateSubject = BehaviorSubject.create<ViewState>()
    private val inputEventSink = PublishSubject.create<UserIntent>()

    override fun processIntents(intents: Observable<UserIntent>) {
        intents.subscribe(inputEventSink)
    }

    override fun states(): Observable<ViewState> =
        processor.loadPostsProcessor
            .map<ViewState> { it.map() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
//        viewStateSubject
            .distinctUntilChanged()
            // Publish the last state on reconnection. Useful when a View rebinds to the ViewModel
            // after the device rotates.
            .replay(1)
            // Create the stream without requiring anyone to subscribe to it. This keeps the stream
            // alive when the UI disconnects.
            .autoConnect(0)
}

private fun Result.map(): ViewState = when (this) {
    ResultLoading -> Loading
    is ResultData<*> -> Data(this.data as List<ListDisplayModel>)
    is ResultError -> Error(errorMessage)
}

class PostsListProcessor(placeholderService: PlaceholderService) {

    val loadPostsProcessor: Observable<Result> = Singles.zip(
        placeholderService.getComments(),
        placeholderService.getPosts(),
        placeholderService.getUsers()
    ).toObservable()
        .map<Result> { (comments, posts, users) ->
            val userMap: Map<Int, User> = users.associateBy { it.userId }
            val commentCountMap = IncrementingMap().apply {
                comments.forEach { put(it.postId) }
            }

            return@map ResultData(
                posts.map {
                    ListDisplayModel(
                        it.title,
                        it.body,
                        commentCountMap[it.id] ?: -1,
                        userMap[it.userId]?.userName ?: "Unknown"
                    )
                }
            )
        }
        .onErrorReturn { ResultError(it.localizedMessage) }
        .startWith(ResultLoading)
}