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

fun AbnfParser.parse(input: String): List<Rule> {
    val listener = AbnfParserListener()
    parseRulelist(input, listener = listener).let {
        if (it != input.length) {
            throw AbnfParserException("Failed to parse beyond index $it")
        }
    }
    return listener.rules
}

class AbnfParserException(message: String?) : Exception(message)

private class AbnfParserListener : AbnfParser.Listener {
    val rules: MutableList<Rule> = mutableListOf()

    private var pendingRuleName: String? = null
    private var pendingIsIncrementalRule: Boolean? = null
    private var pendingRuleElement: Element? = null

    private var pendingRepetitionCount: IntRange? = null

    private val pendingElementsStack: MutableList<MutableList<Element>> = mutableListOf()

    override fun exitRule(input: String, startIndex: Int, endIndex: Int) {
        val name = pendingRuleName.also { pendingRuleName = null }
        val isIncrementalRule = pendingIsIncrementalRule.also { pendingIsIncrementalRule = null }
        val element = pendingRuleElement.also { pendingRuleElement = null }
        if (endIndex == -1) {
            return
        }

        checkNotNull(name)
        checkNotNull(isIncrementalRule)
        checkNotNull(element)
        val index = rules.indexOfFirst { it.name == name }
        if (isIncrementalRule) {
            if (index == -1) {
                throw AbnfParserException("Invalid incremental rule for unknown rule \"$name\"")
            }
            val oldRule = rules[index]
            val oldElement = oldRule.element
            val newElement =
                if (oldElement is Alternation) {
                    oldElement.copy(elements = oldElement.elements + element)
                } else {
                    Alternation(oldElement, element)
                }
            rules[index] = oldRule.copy(element = newElement)
        } else {
            if (index != -1) {
                throw AbnfParserException("Invalid redefinition of existing rule \"$name\"")
            }
            rules += Rule(name, element)
        }
    }

    override fun exitRulename(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        val name = input.substring(startIndex, endIndex)
        if (pendingElementsStack.isEmpty()) {
            pendingRuleName = name
        } else {
            pendingElementsStack.last() += RuleName(name)
        }
    }

    override fun exitDefinedAs(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        pendingIsIncrementalRule = '/' in input.substring(startIndex, endIndex)
    }

    override fun enterElements(input: String, startIndex: Int) {
        pendingElementsStack += mutableListOf<Element>()
    }

    override fun exitElements(input: String, startIndex: Int, endIndex: Int) {
        val elements = pendingElementsStack.removeLast()
        if (endIndex == -1) {
            return
        }

        pendingRuleElement = elements.single()
    }

    override fun enterAlternation(input: String, startIndex: Int) {
        pendingElementsStack += mutableListOf<Element>()
    }

    override fun exitAlternation(input: String, startIndex: Int, endIndex: Int) {
        val elements = pendingElementsStack.removeLast()
        if (endIndex == -1) {
            return
        }

        pendingElementsStack.last() += elements.singleOrNull() ?: Alternation(elements)
    }

    override fun enterConcatenation(input: String, startIndex: Int) {
        pendingElementsStack += mutableListOf<Element>()
    }

    override fun exitConcatenation(input: String, startIndex: Int, endIndex: Int) {
        val elements = pendingElementsStack.removeLast()
        if (endIndex == -1) {
            return
        }

        pendingElementsStack.last() += elements.singleOrNull() ?: Concatenation(elements)
    }

    override fun enterRepetition(input: String, startIndex: Int) {
        pendingElementsStack += mutableListOf<Element>()
    }

    override fun exitRepetition(input: String, startIndex: Int, endIndex: Int) {
        val elements = pendingElementsStack.removeLast()
        val count = pendingRepetitionCount.also { pendingRepetitionCount = null }
        if (endIndex == -1) {
            return
        }

        val element = elements.single()
        pendingElementsStack.last() += if (count != null) Repetition(count, element) else element
    }

    override fun exitRepeat(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        val repeat = input.substring(startIndex, endIndex)
        pendingRepetitionCount =
            if ('*' in repeat) {
                val counts =
                    repeat.split('*', limit = 2).map { if (it.isEmpty()) null else it.toInt() }
                IntRange(counts[0] ?: 0, counts[1] ?: Int.MAX_VALUE)
            } else {
                val count = repeat.toInt()
                IntRange(count, count)
            }
    }

    override fun enterOption(input: String, startIndex: Int) {
        pendingElementsStack += mutableListOf<Element>()
    }

    override fun exitOption(input: String, startIndex: Int, endIndex: Int) {
        val elements = pendingElementsStack.removeLast()
        if (endIndex == -1) {
            return
        }

        val element = elements.single()
        pendingElementsStack.last() += Repetition.ofOptional(element)
    }

    override fun exitCharVal(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        val isCaseSensitive: Boolean
        val string: String
        if (input[startIndex] == '%') {
            isCaseSensitive = input[startIndex + 1].equals('s', true)
            string = input.substring(startIndex + 3, endIndex - 1)
        } else {
            isCaseSensitive = false
            string = input.substring(startIndex + 1, endIndex - 1)
        }
        pendingElementsStack.last() +=
            if (string.length == 1) {
                parseCharVal(string.single(), isCaseSensitive)
            } else {
                val elements = string.map { parseCharVal(it, isCaseSensitive) }
                Concatenation(elements)
            }
    }

    private fun parseCharVal(value: Char, isCaseSensitive: Boolean): TerminalValue =
        when {
            isCaseSensitive -> TerminalValue.of(value)
            value in 'A'..'Z' -> TerminalValue.of(value, value - 'A'.code + 'a'.code)
            value in 'a'..'z' -> TerminalValue.of(value - 'a'.code + 'A'.code, value)
            else -> TerminalValue.of(value)
        }

    override fun exitBinVal(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        pendingElementsStack.last() += parseNumVal(input.substring(startIndex + 1, endIndex), 2)
    }

    override fun exitDecVal(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        pendingElementsStack.last() += parseNumVal(input.substring(startIndex + 1, endIndex), 10)
    }

    override fun exitHexVal(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        pendingElementsStack.last() += parseNumVal(input.substring(startIndex + 1, endIndex), 16)
    }

    private fun parseNumVal(value: String, radix: Int): Element =
        when {
            '-' in value -> {
                val chars = value.split('-', limit = 2).map { it.toInt(radix).toChar() }
                val charRange = chars[0]..chars[1]
                TerminalValue.of(charRange)
            }
            '.' in value -> {
                val elements = value.split('.').map { TerminalValue.of(it.toInt(radix).toChar()) }
                Concatenation(elements)
            }
            else -> {
                val char = value.toInt(radix).toChar()
                TerminalValue.of(char)
            }
        }

    override fun exitProseVal(input: String, startIndex: Int, endIndex: Int) {
        if (endIndex == -1) {
            return
        }

        val name = input.substring(startIndex + 1, endIndex - 1).trim(' ')
        pendingElementsStack.last() += RuleName(name)
    }
}
