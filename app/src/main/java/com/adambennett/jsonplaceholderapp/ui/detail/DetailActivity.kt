package com.adambennett.jsonplaceholderapp.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.list.ListDisplayModel
import com.adambennett.jsonplaceholderapp.utils.consume
import com.adambennett.jsonplaceholderapp.utils.unsafeLazy
import kotlinx.android.synthetic.main.activity_detail.text_view_body as textViewBody
import kotlinx.android.synthetic.main.activity_detail.text_view_number_of_comments as textViewComments
import kotlinx.android.synthetic.main.activity_detail.text_view_title as textViewTitle
import kotlinx.android.synthetic.main.activity_detail.text_view_username as textViewUsername

class DetailActivity : AppCompatActivity() {

    private val displayModel by unsafeLazy {
        intent?.getParcelableExtra(EXTRA_DISPLAY_MODEL) as? ListDisplayModel
            ?: throw IllegalArgumentException("Display model must be passed to Activity via Intent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }

        with(displayModel) {
            textViewTitle.text = title
            textViewBody.text = body
            textViewComments.text = getString(R.string.detail_comments, commentCount)
            textViewUsername.text = getString(R.string.detail_username, username)
        }
    }

    override fun onSupportNavigateUp(): Boolean = consume { finish() }

    companion object {

        private const val EXTRA_DISPLAY_MODEL =
            "com.adambennett.jsonplaceholderapp.EXTRA_DISPLAY_MODEL"

        fun start(activity: Activity, displayModel: ListDisplayModel) {
            Intent(activity, DetailActivity::class.java)
                .apply {
                    putExtra(EXTRA_DISPLAY_MODEL, displayModel)
                }
                .run { activity.startActivity(this) }
        }
    }
}