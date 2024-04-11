package xyz.rthqks.dlog.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class Feature {
    val coroutineScope = CoroutineScope(Job())

    open fun start() {
    }

    open fun stop() {
        coroutineScope.cancel()
    }
}
