package com.adambennett.jsonplaceholderapp.utils

/**
 * If minSdk were 24, I could use merge(it, 1, Int::plus) instead or compute - but this will do
 * for now.
 */
class IncrementingMap : HashMap<Int, Int>() {

    fun put(key: Int) {
        if (containsKey(key)) {
            val i = get(key)!!
            put(key, i + 1)
        } else {
            put(key, 1)
        }
    }
}