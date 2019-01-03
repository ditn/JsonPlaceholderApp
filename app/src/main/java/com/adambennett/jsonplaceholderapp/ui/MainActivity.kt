package com.adambennett.jsonplaceholderapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adambennett.jsonplaceholderapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val model: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model.getPosts()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                },
                onError = {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    it.printStackTrace()
                }
            )
    }
}
