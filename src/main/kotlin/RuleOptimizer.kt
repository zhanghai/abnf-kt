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

internal object RuleOptimizer {
    fun optimize(rule: Rule): Rule = rule.copy(element = optimizeElement(rule.element))

    private fun optimizeElement(element: Element): Element =
        when (element) {
            is RuleName,
            is TerminalValue -> element
            is Repetition -> {
                val repeatedElement =
                    if (element.count.first == 0 && element.count.last == 0) {
                        TerminalValue.of()
                    } else {
                        optimizeElement(element.element)
                    }
                if (element.count.first == 1 && element.count.last == 1) {
                    repeatedElement
                } else {
                    element.copy(element = repeatedElement)
                }
            }
            is Concatenation -> {
                val elements = element.elements.map { optimizeElement(it) }
                elements.singleOrNull() ?: element.copy(elements = elements)
            }
            is Alternation -> {
                val elements = mutableListOf<Element>()
                element.elements
                    .map { optimizeElement(it) }
                    .reduce { accumulator, alternativeElement ->
                        if (accumulator is TerminalValue && alternativeElement is TerminalValue) {
                            accumulator.copy(value = accumulator.value + alternativeElement.value)
                        } else {
                            elements += accumulator
                            alternativeElement
                        }
                    }
                    .let { elements += it }
                elements.singleOrNull() ?: element.copy(elements = elements)
            }
        }
}
