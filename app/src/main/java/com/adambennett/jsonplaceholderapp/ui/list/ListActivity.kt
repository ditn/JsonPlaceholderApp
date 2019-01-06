package com.adambennett.jsonplaceholderapp.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.detail.DetailActivity
import com.adambennett.jsonplaceholderapp.ui.list.adapter.PostsListAdapter
import com.adambennett.jsonplaceholderapp.utils.toast
import com.jakewharton.rxbinding2.support.v4.widget.refreshes
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.android.synthetic.main.activity_main.recycler_view as recyclerView
import kotlinx.android.synthetic.main.activity_main.swipe_refresh_layout as swipeLayout

class ListActivity : AppCompatActivity(), MviView {

    private val compositeDisposable = CompositeDisposable()
    private val model: ListViewModel by viewModel()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PostsListAdapter { onPostSelected(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        compositeDisposable +=
            model.states()
                .subscribeBy { render(it) }

        model.processIntents(intents())
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun intents(): Observable<UserIntent> = swipeLayout.refreshes()
        .map { RefreshIntent }

    override fun render(state: ViewState) {
        when (state) {
            is Error -> toast(state.errorMessage)
            is Data -> adapter.items = state.posts
        }

        swipeLayout.isRefreshing = state is Loading
    }

    private fun onPostSelected(displayModel: ListDisplayModel) {
        DetailActivity.start(this, displayModel)
    }
}