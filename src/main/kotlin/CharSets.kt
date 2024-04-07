/*
 * Copyright 2024 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.zhanghai.kotlin.abnf

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.buildCodeBlock

interface CharSet {
    operator fun contains(char: Char): Boolean

    operator fun plus(other: CharSet): CharSet

    fun generateCode(value: Any): CodeBlock
}

class AsciiCharSet private constructor(private val bits0: Long, private val bits64: Long) :
    CharSet {
    override fun contains(char: Char): Boolean =
        when {
            char < 64.toChar() -> ((1L shl char.code) and bits0) != 0L
            char < 128.toChar() -> ((1L shl (char.code - 64)) and bits64) != 0L
            else -> false
        }

    override fun plus(other: CharSet): CharSet {
        require(other is AsciiCharSet)
        return AsciiCharSet(bits0 or other.bits0, bits64 or other.bits64)
    }

    override fun generateCode(value: Any): CodeBlock = buildCodeBlock {
        beginControlFlow("when")
        // addStatement("%L < 64 -> %L", value, generateCode(value, bits0, 0))
        add("%L < 64.toChar() -> %L", value, generateCode(value, bits0, 0))
        // addStatement("%L < 128 -> %L", value, generateCode(value, bits64, 0))
        add("%L < 128.toChar() -> %L", value, generateCode(value, bits64, 64))
        addStatement("else -> -1")
        endControlFlow()
    }

    private fun generateCode(value: Any, bits: Long, offset: Int): CodeBlock = buildCodeBlock {
        val oneBitCount = bits.countOneBits()
        when {
            oneBitCount == 0 -> add("-1\n")
            oneBitCount == 1 -> {
                val char = bits.countTrailingZeroBits().toChar() + offset
                beginControlFlow("if (%L == %L)", value, char.literal)
                addStatement("index + 1")
                nextControlFlow("else")
                addStatement("-1")
                endControlFlow()
            }
            oneBitCount < 4 -> {
                beginControlFlow("when (%L)", value)
                val charLiterals = buildString {
                    var remainingBits = bits
                    var isFirstChar = true
                    while (remainingBits != 0L) {
                        val bit = remainingBits.takeHighestOneBit()
                        val char = bit.countTrailingZeroBits().toChar() + offset
                        if (isFirstChar) {
                            isFirstChar = false
                        } else {
                            append(", ")
                        }
                        append(char.literal)
                        remainingBits = remainingBits and bit.inv()
                    }
                }
                addStatement("%L -> index + 1", charLiterals)
                addStatement("else -> -1")
                endControlFlow()
            }
            else -> {
                val bitsLiteral = "0x${bits.toULong().toString(16).uppercase()}UL"
                if (offset == 0) {
                    beginControlFlow("if (((1UL shl %L.code) and %L) != 0UL)", value, bitsLiteral)
                } else {
                    beginControlFlow(
                        "if (((1UL shl (%L.code - %L)) and %L) != 0UL)",
                        value,
                        offset,
                        bitsLiteral
                    )
                }
                addStatement("index + 1")
                nextControlFlow("else")
                addStatement("-1")
                endControlFlow()
            }
        }
    }

    private val Char.literal: String
        get() {
            val charLiteralWithoutSingleQuotes =
                when (this) {
                    '\n' -> "\\n"
                    '\'' -> "\\'"
                    '"' -> "\""
                    '$' -> "\$"
                    else ->
                        CodeBlock.of("%S", this)
                            .toString()
                            .let { it.substring(1, it.lastIndex) }
                            .let {
                                if (it.startsWith("\\u")) {
                                    "\\u${it.substring(2).uppercase()}"
                                } else {
                                    it
                                }
                            }
                }
            return "'$charLiteralWithoutSingleQuotes'"
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }
        other as AsciiCharSet
        if (bits0 != other.bits0) {
            return false
        }
        if (bits64 != other.bits64) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = bits0.hashCode()
        result = 31 * result + bits64.hashCode()
        return result
    }

    override fun toString(): String {
        return "AsciiCharSet(bits0=$bits0, bits64=$bits64)"
    }

    companion object {
        fun of(vararg chars: Char): AsciiCharSet {
            var bits0 = 0L
            var bits64 = 0L
            for (char in chars) {
                when {
                    char < 64.toChar() -> bits0 = bits0 or (1L shl char.code)
                    char < 128.toChar() -> bits64 = bits64 or (1L shl (char.code - 64))
                    else -> throw IllegalArgumentException("Non-ASCII char '$char'")
                }
            }
            return AsciiCharSet(bits0, bits64)
        }

        fun of(charRange: CharRange): AsciiCharSet {
            if (charRange.isEmpty()) {
                return AsciiCharSet(0L, 0L)
            }
            val charRangeEnd = charRange.last + 1
            val bits0 =
                when {
                    charRange.first < 64.toChar() -> (1L shl charRange.first.code) - 1L
                    else -> -1L
                } xor
                    when {
                        charRangeEnd < 64.toChar() -> (1L shl charRangeEnd.code) - 1L
                        else -> -1L
                    }
            val bits64 =
                when {
                    charRange.first < 64.toChar() -> 0L
                    charRange.first < 128.toChar() -> (1L shl (charRange.first.code - 64)) - 1L
                    else -> -1L
                } xor
                    when {
                        charRangeEnd < 64.toChar() -> 0L
                        charRangeEnd < 128.toChar() -> (1L shl (charRangeEnd.code - 64)) - 1L
                        else -> -1L
                    }
            return AsciiCharSet(bits0, bits64)
        }
    }
}
