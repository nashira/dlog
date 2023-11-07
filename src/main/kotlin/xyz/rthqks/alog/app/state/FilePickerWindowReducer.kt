package xyz.rthqks.alog.app.state

import xyz.rthqks.alog.intent.Intent
import xyz.rthqks.alog.logic.Reducer

class FilePickerWindowReducer(
    parent: Reducer<*>,
) : Reducer<FilePickerWindowState>(parent) {
    override val state = FilePickerWindowState

    override fun handleIntent(intent: Intent) {}
}