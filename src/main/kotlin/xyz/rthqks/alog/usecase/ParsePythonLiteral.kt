package xyz.rthqks.alog.usecase

import com.github.h0tk3y.betterParse.grammar.parseToEnd
import xyz.rthqks.alog.io.PythonLiteralGrammar

class ParsePythonLiteral {
    //strings, bytes, numbers, tuples, lists, dicts, sets, booleans, None and Ellipsis

    operator fun invoke(input: String): Any? = PythonLiteralGrammar.parseToEnd(input)
}

