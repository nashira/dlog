package xyz.rthqks.alog.usecase

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import xyz.rthqks.alog.model.AlogDocument
import xyz.rthqks.alog.state.Axis
import xyz.rthqks.alog.state.AxisPosition
import xyz.rthqks.alog.state.ChartState
import xyz.rthqks.alog.state.TimeSeries

class CreateChartStateFromAlog {

    operator fun invoke(document: AlogDocument): ChartState {
        val ts = mutableListOf<TimeSeries>()
        val axes = mutableListOf<Axis>()

        if (document.timeStamps.size > 1) {
            val bts = document.timeStamps.mapIndexed { idx, d ->
                Offset(d.toFloat(), document.temp2[idx].toFloat())
            }

            val ets = document.timeStamps.mapIndexed { idx, d ->
                Offset(d.toFloat(), document.temp1[idx].toFloat())
            }

            val bounds = Rect(bts.first().x, 250f, bts.last().x, 0f)
            ts += TimeSeries(bounds, bts, Color.Blue)
            ts += TimeSeries(bounds, ets, Color.Red)

            axes += Axis(25, 5f, bounds, AxisPosition.Left)
            axes += Axis(25, 5f, bounds, AxisPosition.Bottom)
        }

        return ChartState(document.title, axes, ts)
    }
}
