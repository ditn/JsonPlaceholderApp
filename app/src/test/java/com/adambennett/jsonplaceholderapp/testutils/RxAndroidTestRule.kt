package com.adambennett.jsonplaceholderapp.testutils

import com.adambennett.testutils.rxjava.RxInit
import com.adambennett.testutils.testrules.after
import com.adambennett.testutils.testrules.before
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

fun rxInitAndroid(block: RxInitAndroid.() -> Unit) =
    before {
        RxInitAndroid().also(block)
    } after {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(null)
    }

class RxInitAndroid : RxInit() {

    fun main(scheduler: Scheduler) {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }
    }

    fun mainTrampoline() {
        main(Schedulers.trampoline())
    }
}
