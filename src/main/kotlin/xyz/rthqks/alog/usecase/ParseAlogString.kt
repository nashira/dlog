package xyz.rthqks.alog.usecase

import xyz.rthqks.alog.model.AlogDocument

class ParseAlogString {
    operator fun invoke(): AlogDocument {
        return AlogDocument("", emptyList(), emptyList(), emptyList())
    }
}