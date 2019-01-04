package com.adambennett.api.service.testutils

fun Any.getStringFromResource(filePath: String): String =
    this::class.java.getResource("/$filePath").readText()

fun String.asResource(work: (String) -> Unit) {
    work(getStringFromResource(this))
}