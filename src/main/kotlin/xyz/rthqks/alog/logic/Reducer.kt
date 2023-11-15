package xyz.rthqks.alog.logic

import org.koin.core.component.KoinComponent

abstract class Reducer<T>(
    private val parent: Reducer<*>? = null
) : KoinComponent {

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