package xyz.rthqks.alog.usecase

import java.io.File

class GetFileContent {

    operator fun invoke(file: File): String = file.readText()
}