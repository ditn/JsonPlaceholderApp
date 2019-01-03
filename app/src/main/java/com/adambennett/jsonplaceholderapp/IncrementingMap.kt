package com.adambennett.jsonplaceholderapp

class IncrementingMap : HashMap<String, Int>() {

    fun put(key: String) {
        if (containsKey(key)) {
            val i = get(key)!!
            put(key, i + 1)
        } else {
            put(key, 1)
        }
    }
}