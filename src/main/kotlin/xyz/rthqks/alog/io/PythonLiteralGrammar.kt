package xyz.rthqks.alog.io

import com.github.h0tk3y.betterParse.combinators.*
import com.github.h0tk3y.betterParse.grammar.Grammar
import com.github.h0tk3y.betterParse.grammar.parser
import com.github.h0tk3y.betterParse.lexer.literalToken
import com.github.h0tk3y.betterParse.lexer.regexToken
import com.github.h0tk3y.betterParse.parser.Parser

// taken from https://github.com/silmeth/jsonParser and adapted for python
// does not handle complete python 'literal_eval' grammar

object PythonLiteralGrammar : Grammar<Any?>() {
    // the regex "[^\\"]*(\\["nrtbf\\][^\\"]*)*" matches:
    // "               – opening double quote,
    // [^\\"]*         – any number of not escaped characters, nor double quotes
    // (
    //   \\["nrtbf\\]  – backslash followed by special character (\", \n, \r, \\, etc.)
    //   [^\\"]*       – and any number of non-special characters
    // )*              – repeating as a group any number of times
    // "               – closing double quote
    private val stringLiteral by regexToken("\"[^\\\\\"]*(\\\\[\"nrtbf\\\\][^\\\\\"]*)*\"")
    private val stringLiteralSQ by regexToken("'[^\\\\']*(\\\\['nrtbf\\\\][^\\\\']*)*'")

    private val whiteSpace by regexToken("\\s+", ignore = true)

    // Punctuation and parentheses
    private val comma by literalToken(",")
    private val colon by literalToken(":")
    private val openingBrace by literalToken("{")
    private val closingBrace by literalToken("}")
    private val openingBracket by literalToken("[")
    private val closingBracket by literalToken("]")

    // Keywords
    private val noneToken by regexToken("\\bNone\\b")
    private val trueToken by regexToken("\\bTrue\\b")
    private val falseToken by regexToken("\\bFalse\\b")

    // Signs used by numbers
    private val integer by regexToken("\\d+")
    private val dot by literalToken(".")
    private val exponent by regexToken("[eE]")
    private val minus by literalToken("-")

    // python literal values
    private val pythonNull: Parser<Any?> = noneToken asJust null
    private val pythonBool: Parser<Boolean> = (trueToken asJust true) or (falseToken asJust false)
    private val string: Parser<String> = (stringLiteralSQ use { text.substring(1, text.lastIndex) })
        .map {
            it.replace("\\'", "\'")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\u000C")
                .replace("\\\\", "\\")
        } or ((stringLiteral use { text.substring(1, text.lastIndex) })
        .map {
            it.replace("\\\"", "\"")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\b", "\b")
                .replace("\\f", "\u000C")
                .replace("\\\\", "\\")
        })

    // python number literals
    private val exponentPart = -exponent and integer
    private val floatingPointPart = -dot and optional(integer)
    private val onlyFloatingPart = -dot and integer
    private val positiveNumber: Parser<Double> = ((integer and optional(floatingPointPart))
        .map { (int, floatPart) ->
            int.text + (floatPart?.let { ".${it.text}" } ?: "")
        } or
            (onlyFloatingPart map { ".${it.text}" }) and
            optional(exponentPart map { "e${it.text}" }))
        .map { (p1, p2) ->
            (p1 + (p2 ?: "")).toDouble()
        }

    private val number: Parser<Double> = (optional(minus) and positiveNumber)
        .map { (m, num) -> if (m != null) -num else num }

    private val pythonPrimitiveValue: Parser<Any?> = pythonNull or pythonBool or string or number
    private val pythonObject: Parser<Map<String, Any?>> = (-openingBrace and
            separated(string and -colon and parser(this::pythonValue), comma, true) and
            -closingBrace)
        .map {
            it.terms.map { (key, v) -> Pair(key, v) }.toMap()
        }
    private val pythonArray: Parser<List<Any?>> = (-openingBracket and
            separated(parser(this::pythonValue), comma, true) and
            -closingBracket)
        .map { it.terms }
    private val pythonValue: Parser<Any?> = pythonPrimitiveValue or pythonObject or pythonArray
    override val rootParser = pythonValue
}