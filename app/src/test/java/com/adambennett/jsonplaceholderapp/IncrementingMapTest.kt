package com.adambennett.jsonplaceholderapp

import org.amshove.kluent.`should contain`
import org.junit.Test

class IncrementingMapTest {

    @Test
    fun `can add keys`() {
        IncrementingMap()
            .apply {
                put("one")
                put("two")
                put("three")
            }.run {
                this `should contain` ("one" to 1)
                this `should contain` ("two" to 1)
                this `should contain` ("three" to 1)
            }
    }

    @Test
    fun `adding same key more than once increments value`() {
        IncrementingMap()
            .apply {
                put("one")
                put("one")
                put("one")
            }.run {
                this `should contain` ("one" to 3)
            }
    }
}