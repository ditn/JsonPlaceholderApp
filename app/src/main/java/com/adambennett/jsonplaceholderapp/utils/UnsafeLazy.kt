package com.adambennett.jsonplaceholderapp.utils

fun <T> unsafeLazy(block: () -> T) = lazy(LazyThreadSafetyMode.NONE) { block() }