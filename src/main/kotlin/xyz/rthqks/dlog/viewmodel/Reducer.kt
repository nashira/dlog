package xyz.rthqks.dlog.viewmodel

import xyz.rthqks.dlog.intent.Intent

abstract class Reducer<T>(
    private val parent: Reducer<*>? = null
) {

    abstract val state: T

    protected abstract fun handleIntent(intent: Intent)

    operator fun invoke(intent: Intent) {
        println("intent $intent")
        handleIntent(intent)
        parent?.handleIntent(intent)
    }

    fun bind(intent: Intent): () -> Unit = {
        this(intent)
    }
}