package com.adambennett.jsonplaceholderapp.ui

import androidx.lifecycle.ViewModel
import com.adambennett.api.service.PlaceholderService
import com.adambennett.api.service.models.Post
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val placeholderService: PlaceholderService
) : ViewModel() {

    // TODO: This is only for testing  
    fun getPosts() : Single<List<Post>> = placeholderService.getPosts()
        .subscribeOn(Schedulers.io())
}