package com.adambennett.network.interfaces

/**
 * Timber depends on Android, so are unsuitable for plain Kotlin modules.
 * Use Timber where possible, but where not, inject and use [Logger].
 */
interface Logger {

    fun d(s: String)

    fun v(s: String)

    fun e(s: String)
}
