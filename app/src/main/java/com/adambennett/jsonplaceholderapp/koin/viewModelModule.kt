package com.adambennett.jsonplaceholderapp.koin

import com.adambennett.jsonplaceholderapp.ui.list.ListModel
import com.adambennett.jsonplaceholderapp.ui.list.PostsListProcessor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { ListModel(PostsListProcessor(get())) }
}