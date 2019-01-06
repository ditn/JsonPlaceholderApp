package com.adambennett.jsonplaceholderapp

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