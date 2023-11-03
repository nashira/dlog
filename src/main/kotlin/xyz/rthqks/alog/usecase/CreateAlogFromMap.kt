package xyz.rthqks.alog.usecase

import xyz.rthqks.alog.model.AlogDocument

class CreateAlogFromMap {

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(any: Any?): AlogDocument? {
        val map = any as? Map<String, Any?> ?: return null

        val title = map["title"] as? String ?: return null
        val timestamps = map["timex"] as? List<Double> ?: emptyList()
        val temp1 = map["temp1"] as? List<Double> ?: emptyList()
        val temp2 = map["temp2"] as? List<Double> ?: emptyList()
        return AlogDocument(title, timestamps, temp1, temp2)
    }
}