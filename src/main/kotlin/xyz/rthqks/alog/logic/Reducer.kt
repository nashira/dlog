package xyz.rthqks.alog.logic

import xyz.rthqks.alog.intent.Intent

abstract class Reducer {

    abstract operator fun invoke(intent: Intent)

    fun bind(intent: Intent): () -> Unit = {
        this(intent)
    }
}