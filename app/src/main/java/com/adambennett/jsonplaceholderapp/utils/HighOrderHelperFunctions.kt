package com.adambennett.jsonplaceholderapp.utils

fun consume(func : () -> Unit): Boolean {
    func()
    return true
}