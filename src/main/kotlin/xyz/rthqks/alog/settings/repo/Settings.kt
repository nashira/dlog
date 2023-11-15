package xyz.rthqks.alog.settings.repo

class Settings(
    private val map: MutableMap<Key<*>, Any?> = mutableMapOf()
) : Map<Key<*>, Any?> by map {
    operator fun <T> set(key: Key<T>, value: T) {
        this[key] = value
    }
//
//    @Suppress("UNCHECKED_CAST")
//    operator fun <T> get(key: Key<T>): T = this[key] as T
}

data class Setting<T>(
    val key: Key<T>,
    val value: T
)

interface Key<T>
