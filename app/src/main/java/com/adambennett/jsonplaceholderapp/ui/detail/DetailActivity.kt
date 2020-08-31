package com.adambennett.jsonplaceholderapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Icon
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.contentColor
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.list.ListDisplayModel
import com.adambennett.jsonplaceholderapp.utils.consume
import com.adambennett.jsonplaceholderapp.utils.extraNotNull

class DetailActivity : AppCompatActivity() {

    private val displayModel: ListDisplayModel by extraNotNull(EXTRA_DISPLAY_MODEL)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Detail(displayModel) {
                    finish()
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean = consume { finish() }

    companion object {

        private const val EXTRA_DISPLAY_MODEL =
            "com.adambennett.jsonplaceholderapp.EXTRA_DISPLAY_MODEL"

        fun start(activity: Activity, displayModel: ListDisplayModel) {
            Intent(activity, DetailActivity::class.java)
                .apply { putExtra(EXTRA_DISPLAY_MODEL, displayModel) }
                .run { activity.startActivity(this) }
        }
    }
}

@Composable
fun Detail(displayModel: ListDisplayModel, onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Detail",
                        color = contentColor()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack)
                    }
                }
            )
        },
        bodyContent = {
            ScrollableColumn(modifier = Modifier.padding(16.dp)) {
                Text(text = displayModel.title, style = MaterialTheme.typography.h3)

                Row(modifier = Modifier.padding(top = 16.dp)) {
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = stringResource(
                            R.string.detail_comments,
                            displayModel.commentCount
                        )
                    )
                    Text(
                        modifier = Modifier.weight(1.0f),
                        text = stringResource(
                            R.string.detail_username,
                            displayModel.username
                        )
                    )
                }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = displayModel.body
                )
            }
        }
    )
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colors.surface) {
            Detail(
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
        }
    }
}
