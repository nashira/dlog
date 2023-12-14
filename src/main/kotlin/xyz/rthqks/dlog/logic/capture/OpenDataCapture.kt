package xyz.rthqks.dlog.logic.capture

import xyz.rthqks.dlog.io.DataCaptureConfig
import xyz.rthqks.dlog.io.DataCaptureService

class OpenDataCapture(
    private val dataCaptureService: DataCaptureService
) {

    suspend operator fun invoke(config: DataCaptureConfig) {
        dataCaptureService.open(config)
    }
}