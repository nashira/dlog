package xyz.rthqks.alog.logic

import org.koin.core.component.KoinComponent
import xyz.rthqks.alog.intent.Intent

abstract class Reducer(
    private val parent: Reducer? = null
) : KoinComponent {

    protected abstract fun handleIntent(intent: Intent)

    operator fun invoke(intent: Intent) {
        handleIntent(intent)
        parent?.handleIntent(intent)
    }

    fun bind(intent: Intent): () -> Unit = {
        this(intent)
    }
}