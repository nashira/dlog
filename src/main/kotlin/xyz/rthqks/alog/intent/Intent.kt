package xyz.rthqks.alog.intent

import xyz.rthqks.alog.model.Setting
import xyz.rthqks.alog.state.WindowState
import java.io.File

sealed class Intent {
    abstract class Settings : Intent()
}

data object RefreshData : Intent()
data object ShowFileSelector : Intent()
data object ShowEditSettings : Intent()
data class SelectFiles(val files: List<File>) : Intent()
data class CloseWindow(val window: WindowState) : Intent()

data class ChangeSetting(val setting: Setting) : Intent.Settings()
