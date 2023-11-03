package xyz.rthqks.alog.intent

import xyz.rthqks.alog.state.WindowState
import java.io.File

sealed class Intent
data object RefreshData : Intent()
data object ShowFileSelector : Intent()
data class SelectFiles(val files: List<File>) : Intent()
data class CloseWindow(val window: WindowState) : Intent()