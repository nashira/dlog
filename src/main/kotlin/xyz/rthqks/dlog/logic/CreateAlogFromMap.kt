package xyz.rthqks.dlog.logic

import xyz.rthqks.dlog.io.AlogDocument

class CreateAlogFromMap {

    @Suppress("UNCHECKED_CAST")
    operator fun invoke(fileName: String, any: Any?): AlogDocument? {
        val map = any as? Map<String, Any?> ?: return null

        val title = map["title"] as? String ?: return null
        val timestamps = map["timex"] as? List<Double> ?: emptyList()
        val temp1 = map["temp1"] as? List<Double> ?: emptyList()
        val temp2 = map["temp2"] as? List<Double> ?: emptyList()
        val timeIndex = (map["timeindex"] as? List<Double>)?.map { it.toInt() } ?: emptyList()
        val specialEvents = (map["specialevents"] as? List<Double>)?.map { it.toInt() } ?: emptyList()
        val specialEventTypes = (map["specialeventstype"] as? List<Double>)?.map { it.toInt() } ?: emptyList()
        val specialEventValues = map["specialeventsvalue"] as? List<Double> ?: emptyList()

        val computed = map["computed"] as? Map<String, Any?> ?: emptyMap()

        return AlogDocument(
            fileName,
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
            specialEvents,
            specialEventTypes,
            specialEventValues,
            (computed["TP_idx"] as? Double)?.toInt() ?: -1
        )
    }
}