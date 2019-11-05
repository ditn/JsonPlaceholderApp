package com.adambennett.jsonplaceholderapp.utils

import org.amshove.kluent.`should contain`
import org.junit.Test

class IncrementingMapTest {

    @Test
    fun `can add keys`() {
        IncrementingMap()
            .apply {
                put(1)
                put(2)
                put(3)
            }.run {
                this `should contain` (1 to 1)
                this `should contain` (2 to 1)
                this `should contain` (3 to 1)
            }
    }

    @Test
    fun `adding same key more than once increments value`() {
        IncrementingMap()
            .apply {
                put(1)
                put(1)
                put(1)
            }.run {
                this `should contain` (1 to 3)
            }
    }
}
