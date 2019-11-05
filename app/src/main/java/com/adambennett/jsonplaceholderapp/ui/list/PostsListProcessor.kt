package com.adambennett.jsonplaceholderapp.ui.list

import com.adambennett.api.service.PlaceholderService
import com.adambennett.api.service.models.User
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsResult
import com.adambennett.jsonplaceholderapp.ui.mvi.MviActionProcessor
import com.adambennett.jsonplaceholderapp.utils.IncrementingMap
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * In a more complex app, I'd instead pass in a Repository here which handles fetching, caching and
 * storing data offline using Room, with different fetching strategies for local-first and force
 * refresh. You generally wouldn't pass a network service in here directly.
 */
class PostsListProcessor(placeholderService: PlaceholderService) :
    MviActionProcessor<PostsAction, PostsResult>() {

    override fun getActionProcessors(
        shared: Observable<PostsAction>
    ): List<Observable<PostsResult>> = listOf(shared.connect(loadPostsProcessor))

    private val loadPostsProcessor: ObservableTransformer<PostsAction, PostsResult> =
        ObservableTransformer { actions ->
            actions.switchMap {
                Singles.zip(
                    placeholderService.getComments(),
                    placeholderService.getPosts(),
                    placeholderService.getUsers()
                ).subscribeOn(Schedulers.io())
                    .toObservable()
                    .map { (comments, posts, users) ->
                        val userMap: Map<Int, User> = users.associateBy { it.id }
                        val commentCountMap = IncrementingMap().apply {
                            comments.forEach { put(it.postId) }
                        }

                        return@map PostsResult.Success(
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
                    .cast(PostsResult::class.java)
                    .doOnError { Timber.e(it) }
                    .onErrorReturn { PostsResult.Error(it.message ?: "Error loading posts") }
                    .startWith(PostsResult.Loading)
            }
        }
}
