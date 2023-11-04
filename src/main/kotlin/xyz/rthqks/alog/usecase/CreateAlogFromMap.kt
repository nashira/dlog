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
        val timeIndex = (map["timeindex"] as? List<Double> ?: emptyList()).map { it.toInt() }

        return AlogDocument(
            title,
            map["xmin"] as? Double ?: 0.0,
            map["xmax"] as? Double ?: 1.0,
            map["ymin"] as? Double ?: 0.0,
            map["ymax"] as? Double ?: 250.0,
            map["zmin"] as? Double ?: 0.0,
            map["zmax"] as? Double ?: 25.0,
            timestamps,
            temp1,
            temp2,
            timeIndex,
        )
    }
}