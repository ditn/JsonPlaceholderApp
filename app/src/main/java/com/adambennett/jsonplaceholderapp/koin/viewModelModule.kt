package com.adambennett.jsonplaceholderapp.koin

import com.adambennett.jsonplaceholderapp.ui.list.ListModel
import com.adambennett.jsonplaceholderapp.ui.list.PostsListProcessor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListModel(PostsListProcessor(get())) }
}
