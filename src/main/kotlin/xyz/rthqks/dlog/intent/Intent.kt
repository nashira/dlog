package xyz.rthqks.dlog.intent

import xyz.rthqks.dlog.state.WindowState
import xyz.rthqks.dlog.io.AlogDocument
import xyz.rthqks.dlog.repo.Setting
import java.io.File

interface Intent
interface SettingsIntent : Intent

data object FindFileToOpen : Intent
data object FindFileToReplay : Intent
data object ShowEditSettings : Intent
data class SelectFiles(val files: List<File>) : Intent
data class CloseWindow(val window: WindowState) : Intent
data class OpenChartWindow(val alogDocument: AlogDocument) : Intent {
    override fun toString(): String {
        return "OpenChartWindow(${alogDocument.fileName})"
    }
}

data class ReplayChartWindow(val alogDocument: AlogDocument) : Intent {
    override fun toString(): String {
        return "ReplayChartWindow(${alogDocument.fileName})"
    }
}

data class ChangeSetting(val setting: Setting<*>) : SettingsIntent
