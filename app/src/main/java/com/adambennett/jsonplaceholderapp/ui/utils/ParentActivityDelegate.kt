package com.adambennett.jsonplaceholderapp.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("unused")
class ParentActivityDelegate<T>(
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T>, LifecycleObserver {

    private var value: T? = null

    init {
        fragment.lifecycle.addObserver(this)
    }

    @Suppress("UNCHECKED_CAST")
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun create() {
        value = fragment.activity as? T
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun destroy() {
        value = null
    }

    operator fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        throw IllegalStateException("Cannot set value on a ReadOnlyProperty")
    }

    override operator fun getValue(thisRef: Fragment, property: KProperty<*>): T = value!!
}