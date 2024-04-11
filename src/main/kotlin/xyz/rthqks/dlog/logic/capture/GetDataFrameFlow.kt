package xyz.rthqks.dlog.logic.capture

import xyz.rthqks.dlog.io.DataCaptureConfig
import xyz.rthqks.dlog.io.DataCaptureService

class GetDataFrameFlow(
    private val dataCaptureService: DataCaptureService
) {

    operator fun invoke(config: DataCaptureConfig) = dataCaptureService.receiveFlow(config)
}