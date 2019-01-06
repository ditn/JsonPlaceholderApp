package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.api.service.PlaceholderService
import com.adambennett.api.service.models.User
import com.adambennett.jsonplaceholderapp.ui.mvi.Action
import com.adambennett.jsonplaceholderapp.ui.mvi.LoadPostsAction
import com.adambennett.jsonplaceholderapp.ui.mvi.Result
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultData
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultError
import com.adambennett.jsonplaceholderapp.ui.mvi.ResultLoading
import com.adambennett.jsonplaceholderapp.utils.IncrementingMap
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers

/**
 * In a more complex app, I'd instead pass in a Repository here which handles fetching, caching and
 * storing data offline using Room, with different fetching strategies for local-first and force
 * refresh. You generally wouldn't pass a network service in here directly.
 */
class PostsListProcessor(placeholderService: PlaceholderService) {

    private val loadPostsProcessor: Observable<Result> = Singles.zip(
        placeholderService.getComments(),
        placeholderService.getPosts(),
        placeholderService.getUsers()
    ).toObservable()
        .map { (comments, posts, users) ->
            val userMap: Map<Int, User> = users.associateBy { it.id }
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
        .cast(Result::class.java)
        .onErrorReturn { ResultError(it.message ?: "") }
        .startWith(ResultLoading)
        .subscribeOn(Schedulers.io())

    val actionProcessor = ObservableTransformer<Action, Result> { actions ->
        actions.publish { shared ->
            Observable.merge(
                shared.ofType(LoadPostsAction::class.java)
                    .flatMap { loadPostsProcessor },
                // Check for unhandled Action types here
                shared.filter { it !is LoadPostsAction }
                    .flatMap {
                        Observable.error<Result>(
                            IllegalArgumentException("Unknown action type $it")
                        )
                    }
            )
        }
    }
}