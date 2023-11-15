package xyz.rthqks.dlog.logic

import java.io.File

class GetFileContent {
    operator fun invoke(file: File): String = file.readText()
}