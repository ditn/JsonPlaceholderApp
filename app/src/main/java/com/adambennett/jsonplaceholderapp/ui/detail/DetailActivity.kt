package com.adambennett.jsonplaceholderapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.core.setContent
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.FlexRow
import androidx.ui.layout.Padding
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeColor
import androidx.ui.material.themeTextStyle
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.list.ListDisplayModel
import com.adambennett.jsonplaceholderapp.utils.consume
import com.adambennett.jsonplaceholderapp.utils.unsafeLazy

class DetailActivity : AppCompatActivity() {

    private val displayModel by unsafeLazy {
        intent?.getParcelableExtra(EXTRA_DISPLAY_MODEL) as? ListDisplayModel
            ?: throw IllegalArgumentException("Display model must be passed to Activity via Intent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme { Detail(displayModel) }
        }

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
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
fun Detail(displayModel: ListDisplayModel) {
    Padding(16.dp) {
        Column {
            Text(text = displayModel.title, style = +themeTextStyle { h4 })
            Divider(height = 16.dp, color = Color.Transparent)
            FlexRow {
                expanded(1.0f) {
                    Text(
                        text = +stringResource(
                            R.string.detail_comments,
                            displayModel.commentCount
                        )
                    )
                    Text(
                        text = +stringResource(
                            R.string.detail_username,
                            displayModel.username
                        )
                    )
                }
            }
            Divider(height = 16.dp, color = Color.Transparent)
            Text(text = displayModel.body)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Surface(color = +themeColor { surface }) {
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
