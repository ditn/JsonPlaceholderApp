package com.adambennett.jsonplaceholderapp.koin

import android.preference.ListPreference
import com.adambennett.jsonplaceholderapp.ui.list.ListViewModel
import com.adambennett.jsonplaceholderapp.ui.list.PostsListProcessor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {

    viewModel { ListViewModel(PostsListProcessor(get())) }
}