package xyz.rthqks.dlog.feature

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class Feature {
    val coroutineScope = CoroutineScope(Job())
//    override val scope: Scope by lazy { createScope().apply { declare(this) } }

//    open fun act(intent: Intent) {
//
//    }

    open fun close() {
        coroutineScope.cancel()
    }
}
