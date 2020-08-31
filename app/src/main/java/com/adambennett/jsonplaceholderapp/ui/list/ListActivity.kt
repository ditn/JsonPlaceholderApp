package com.adambennett.jsonplaceholderapp.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rxjava2.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.detail.DetailActivity
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsAction
import com.adambennett.jsonplaceholderapp.ui.list.models.PostsViewState
import com.adambennett.jsonplaceholderapp.ui.list.models.UserIntent
import com.adambennett.jsonplaceholderapp.ui.mvi.MviView
import com.adambennett.jsonplaceholderapp.utils.toast
import com.adambennett.jsonplaceholderapp.utils.unsafeLazy
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity(), MviView<UserIntent, PostsAction, PostsViewState> {

    /**
     * If there were more user actions, here we'd merge the Observables into a single stream of
     * UserIntent objects.
     */
    override val intents: Observable<UserIntent> by unsafeLazy {
//        swipeLayout.refreshes()
        Observable.empty<UserIntent>()
            .map { UserIntent.Refresh }
            .cast(UserIntent::class.java)
            .startWith(UserIntent.InitialIntent)
            .observeOn(AndroidSchedulers.mainThread(), false)
    }

    private val model: ListModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListScreen(viewState = model.states) {
                DetailActivity.start(this, it)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        model.processIntents(intents)
    }

    override fun render(state: PostsViewState) {
        with(state) {
//            swipeLayout.isRefreshing = refreshing
            error?.consume { toast(it ?: "Error fetching posts") }
        }
    }
}

@Composable
private fun ListScreen(
    viewState: Observable<PostsViewState>,
    click: (ListDisplayModel) -> Unit = { }
) {
    val state: PostsViewState by viewState.subscribeAsState(initial = PostsViewState())

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.app_name),
                            color = contentColor()
                        )
                    },
                )
            },
            bodyContent = {
                ListBodyContent(list = state.data, click = click)
            }
        )
    }
}

@Composable
private fun ListBodyContent(list: List<ListDisplayModel>, click: (ListDisplayModel) -> Unit = { }) {
    LazyColumnFor(items = list, modifier = Modifier.fillMaxSize()) { item ->
        ListItem(
            modifier = Modifier.clickable(onClick = { click(item) }),
            text = { item.title }
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    ListScreen(
        viewState = Observable.just(
            PostsViewState(
                data = listOf(
                    ListDisplayModel(
                        title = "sunt aut facere repellat provident occaecati excepturi optio" +
                            " reprehenderit",
                        body = "quia et suscipit\n" +
                            "suscipit recusandae consequuntur expedita et cum\n" +
                            "reprehenderit molestiae ut ut quas totam\n" +
                            "nostrum rerum est autem sunt rem eveniet architecto",
                        commentCount = 4,
                        username = "Bret"
                    )
                )
            )
        )
    )
}
