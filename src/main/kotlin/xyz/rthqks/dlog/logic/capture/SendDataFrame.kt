package xyz.rthqks.dlog.logic.capture

import xyz.rthqks.dlog.io.DataCaptureConfig
import xyz.rthqks.dlog.io.DataCaptureService

class SendDataFrame(
    private val dataCaptureService: DataCaptureService
) {

    operator fun invoke(config: DataCaptureConfig, value: String) {
        dataCaptureService.send(config, value)
    }
}
