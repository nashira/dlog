package xyz.rthqks.alog.logic

import xyz.rthqks.alog.app.state.WindowState
import xyz.rthqks.alog.model.AlogDocument
import xyz.rthqks.alog.settings.repo.Setting
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
