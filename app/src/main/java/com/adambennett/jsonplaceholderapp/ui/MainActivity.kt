package com.adambennett.jsonplaceholderapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.adambennett.jsonplaceholderapp.R
import com.adambennett.jsonplaceholderapp.ui.detail.DetailDisplayModel
import com.adambennett.jsonplaceholderapp.ui.detail.DetailFragment
import com.adambennett.jsonplaceholderapp.ui.list.ListFragment

class MainActivity : AppCompatActivity(), MainActivityCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, ListFragment.newInstance())
            .commit()
    }

    override fun onPostSelected(displayModel: DetailDisplayModel) {
        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragment_container,
                DetailFragment.newInstance(
                    DetailDisplayModel(
                        "title",
                        "body",
                        1,
                        "username"
                    )
                )
            )
            .commit()
    }
}

interface MainActivityCallback {

    fun onPostSelected(displayModel: DetailDisplayModel)
}
