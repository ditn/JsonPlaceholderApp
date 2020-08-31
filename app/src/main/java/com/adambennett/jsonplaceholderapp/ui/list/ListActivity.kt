package com.adambennett.jsonplaceholderapp.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.detail.DetailActivity
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsViewState
import com.adambennett.jsonplaceholderapp.ui.list.models.UserIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.MviView
import com.adambennett.jsonplaceholderapp.utils.toast
import com.adambennett.jsonplaceholderapp.utils.unsafeLazy
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity(), MviView<UserIntent, PostsAction, PostsViewState> {

    /**
     * If there were more user actions, here we'd merge the Observables into a single stream of
     * UserIntent objects.
     */
    override val intents: Observable<UserIntent> by unsafeLazy {
//        swipeLayout.refreshes()
//            .map { UserIntent.Refresh }
//            .cast(UserIntent::class.java)
//             Emit initial intent on first subscribe to trigger initial loading
//            .startWith(UserIntent.InitialIntent)
//            .observeOn(AndroidSchedulers.mainThread(), false)
        TODO()
    }

    private val compositeDisposable = CompositeDisposable()
    private val model: ListModel by viewModel()
//    private val adapter by unsafeLazy { PostsListAdapter { onPostSelected(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable +=
            model.states
                .subscribeBy(onNext = this::render)

        model.processIntents(intents)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun render(state: PostsViewState) {
        with(state) {
//            swipeLayout.isRefreshing = refreshing
//            adapter.items = data
            error?.consume { toast(it ?: "Error fetching posts") }
        }
    }

    private fun onPostSelected(displayModel: ListDisplayModel) {
        DetailActivity.start(this, displayModel)
    }
}
