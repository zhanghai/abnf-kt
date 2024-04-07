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

sealed interface Element

data class RuleName(val name: String) : Element {
    init {
        Rule.requireValidName(name)
    }
}

data class TerminalValue(val value: CharSet) : Element {
    companion object {
        fun of(vararg chars: Char): TerminalValue = TerminalValue(AsciiCharSet.of(*chars))

        fun of(charRange: CharRange): TerminalValue = TerminalValue(AsciiCharSet.of(charRange))
    }
}

data class Repetition(val count: IntRange, val element: Element) : Element {
    init {
        require(!count.isEmpty()) { "Empty repetition" }
        require(count.first >= 0) { "Negative repetition" }
    }

    companion object {
        fun ofAny(element: Element): Repetition = Repetition(IntRange(0, Int.MAX_VALUE), element)

        fun ofAtLeast(count: Int, element: Element): Repetition =
            Repetition(IntRange(count, Int.MAX_VALUE), element)

        fun ofAtMost(count: Int, element: Element): Repetition =
            Repetition(IntRange(0, count), element)

        fun ofOptional(element: Element): Repetition = Repetition(IntRange(0, 1), element)
    }
}

data class Concatenation(val elements: List<Element>) : Element {
    init {
        require(elements.size >= 2) { "Empty or single concatenation" }
    }

    constructor(vararg elements: Element) : this(elements.toList())
}

data class Alternation(val elements: List<Element>) : Element {
    init {
        require(elements.size >= 2) { "Empty or single alternation" }
    }

    constructor(vararg elements: Element) : this(elements.toList())
}
