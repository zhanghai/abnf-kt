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

@Suppress(
    "LABEL_NAME_CLASH",
    "MemberVisibilityCanBePrivate",
    "NAME_SHADOWING",
    "RedundantVisibilityModifier",
    "UnnecessaryVariable",
)
public object AbnfParser {
    public fun parseRulelist(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRulelist(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                var count = 0
                while (true) {
                    run {
                            parseRule(input, index, listener).let {
                                if (it != -1) {
                                    return@run it
                                }
                            }
                            run {
                                    var index = index
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                parseCWsp(input, index, listener).let {
                                                    if (it == -1) {
                                                        return@let false
                                                    }
                                                    ++count
                                                    index = it
                                                    true
                                                } || break
                                            }
                                            index
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    parseCNl(input, index, listener).let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                    index
                                }
                                .let {
                                    if (it != -1) {
                                        return@run it
                                    }
                                }
                            -1
                        }
                        .let {
                            if (it == -1) {
                                return@let false
                            }
                            ++count
                            index = it
                            true
                        } || break
                }
                if (count < 1) {
                    return@run -1
                }
                index
            }
            .also { listener?.exitRulelist(input, startIndex, it) }
    }

    public fun parseRule(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRule(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseRulename(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                parseDefinedAs(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                parseElements(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                parseCNl(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitRule(input, startIndex, it) }
    }

    public fun parseRulename(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRulename(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseAlpha(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    parseAlpha(input, index).let {
                                        if (it != -1) {
                                            return@run it
                                        }
                                    }
                                    parseDigit(input, index).let {
                                        if (it != -1) {
                                            return@run it
                                        }
                                    }
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '-') {
                                                        index + 1
                                                    } else {
                                                        -1
                                                    }
                                                char < 128.toChar() -> -1
                                                else -> -1
                                            }
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    -1
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitRulename(input, startIndex, it) }
    }

    public fun parseDefinedAs(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterDefinedAs(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        run {
                                if (index >= input.length) {
                                    return@run -1
                                }
                                val char = input[index]
                                when {
                                    char < 64.toChar() ->
                                        if (char == '=') {
                                            index + 1
                                        } else {
                                            -1
                                        }
                                    char < 128.toChar() -> -1
                                    else -> -1
                                }
                            }
                            .let {
                                if (it != -1) {
                                    return@run it
                                }
                            }
                        run {
                                var index = index
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == '=') {
                                                    index + 1
                                                } else {
                                                    -1
                                                }
                                            char < 128.toChar() -> -1
                                            else -> -1
                                        }
                                    }
                                    .let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == '/') {
                                                    index + 1
                                                } else {
                                                    -1
                                                }
                                            char < 128.toChar() -> -1
                                            else -> -1
                                        }
                                    }
                                    .let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                index
                            }
                            .let {
                                if (it != -1) {
                                    return@run it
                                }
                            }
                        -1
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitDefinedAs(input, startIndex, it) }
    }

    public fun parseElements(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterElements(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseAlternation(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitElements(input, startIndex, it) }
    }

    public fun parseCWsp(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterCWsp(input, startIndex)
        val index = startIndex
        return run {
                parseWsp(input, index).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                run {
                        var index = index
                        parseCNl(input, index, listener).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
                        }
                        parseWsp(input, index).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
                        }
                        index
                    }
                    .let {
                        if (it != -1) {
                            return@run it
                        }
                    }
                -1
            }
            .also { listener?.exitCWsp(input, startIndex, it) }
    }

    public fun parseCNl(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterCNl(input, startIndex)
        val index = startIndex
        return run {
                parseComment(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseCrlf(input, index).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitCNl(input, startIndex, it) }
    }

    public fun parseComment(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterComment(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == ';') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    parseWsp(input, index).let {
                                        if (it != -1) {
                                            return@run it
                                        }
                                    }
                                    parseVchar(input, index).let {
                                        if (it != -1) {
                                            return@run it
                                        }
                                    }
                                    -1
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseCrlf(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitComment(input, startIndex, it) }
    }

    public fun parseAlternation(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterAlternation(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseConcatenation(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    var index = index
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                parseCWsp(input, index, listener).let {
                                                    if (it == -1) {
                                                        return@let false
                                                    }
                                                    ++count
                                                    index = it
                                                    true
                                                } || break
                                            }
                                            index
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '/') {
                                                        index + 1
                                                    } else {
                                                        -1
                                                    }
                                                char < 128.toChar() -> -1
                                                else -> -1
                                            }
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                parseCWsp(input, index, listener).let {
                                                    if (it == -1) {
                                                        return@let false
                                                    }
                                                    ++count
                                                    index = it
                                                    true
                                                } || break
                                            }
                                            index
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    parseConcatenation(input, index, listener).let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                    index
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitAlternation(input, startIndex, it) }
    }

    public fun parseConcatenation(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterConcatenation(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseRepetition(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    var index = index
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                parseCWsp(input, index, listener).let {
                                                    if (it == -1) {
                                                        return@let false
                                                    }
                                                    ++count
                                                    index = it
                                                    true
                                                } || break
                                            }
                                            if (count < 1) {
                                                return@run -1
                                            }
                                            index
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    parseRepetition(input, index, listener).let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                    index
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitConcatenation(input, startIndex, it) }
    }

    public fun parseRepetition(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRepetition(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            parseRepeat(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseElement(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitRepetition(input, startIndex, it) }
    }

    public fun parseRepeat(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRepeat(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        run {
                                var index = index
                                var count = 0
                                while (true) {
                                    parseDigit(input, index).let {
                                        if (it == -1) {
                                            return@let false
                                        }
                                        ++count
                                        index = it
                                        true
                                    } || break
                                }
                                index
                            }
                            .let {
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                if (index >= input.length) {
                                    return@run -1
                                }
                                val char = input[index]
                                when {
                                    char < 64.toChar() ->
                                        if (char == '*') {
                                            index + 1
                                        } else {
                                            -1
                                        }
                                    char < 128.toChar() -> -1
                                    else -> -1
                                }
                            }
                            .let {
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                var index = index
                                var count = 0
                                while (true) {
                                    parseDigit(input, index).let {
                                        if (it == -1) {
                                            return@let false
                                        }
                                        ++count
                                        index = it
                                        true
                                    } || break
                                }
                                index
                            }
                            .let {
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        index
                    }
                    .let {
                        if (it != -1) {
                            return@run it
                        }
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseDigit(input, index).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        if (count < 1) {
                            return@run -1
                        }
                        index
                    }
                    .let {
                        if (it != -1) {
                            return@run it
                        }
                    }
                -1
            }
            .also { listener?.exitRepeat(input, startIndex, it) }
    }

    public fun parseElement(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterElement(input, startIndex)
        val index = startIndex
        return run {
                parseRulename(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseGroup(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseOption(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseCharVal(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseNumVal(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseProseVal(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitElement(input, startIndex, it) }
    }

    public fun parseGroup(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterGroup(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == '(') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseAlternation(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == ')') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitGroup(input, startIndex, it) }
    }

    public fun parseOption(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterOption(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                if (char == '[') {
                                    index + 1
                                } else {
                                    -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseAlternation(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseCWsp(input, index, listener).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                if (char == ']') {
                                    index + 1
                                } else {
                                    -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitOption(input, startIndex, it) }
    }

    public fun parseCharVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterCharVal(input, startIndex)
        val index = startIndex
        return run {
                parseCaseInsensitiveString(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseCaseSensitiveString(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitCharVal(input, startIndex, it) }
    }

    public fun parseCaseInsensitiveString(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterCaseInsensitiveString(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            run {
                                    var index = index
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '%') {
                                                        index + 1
                                                    } else {
                                                        -1
                                                    }
                                                char < 128.toChar() -> -1
                                                else -> -1
                                            }
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() -> -1
                                                char < 128.toChar() ->
                                                    when (char) {
                                                        'i',
                                                        'I' -> index + 1
                                                        else -> -1
                                                    }
                                                else -> -1
                                            }
                                        }
                                        .let {
                                            if (it == -1) {
                                                return@run -1
                                            }
                                            index = it
                                        }
                                    index
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseQuotedString(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitCaseInsensitiveString(input, startIndex, it) }
    }

    public fun parseCaseSensitiveString(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterCaseSensitiveString(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == '%') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                when (char) {
                                    's',
                                    'S' -> index + 1
                                    else -> -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseQuotedString(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitCaseSensitiveString(input, startIndex, it) }
    }

    public fun parseQuotedString(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterQuotedString(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseDquote(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    if (index >= input.length) {
                                        return@run -1
                                    }
                                    val char = input[index]
                                    when {
                                        char < 64.toChar() ->
                                            if (
                                                ((1UL shl char.code) and 0xFFFFFFFB00000000UL) !=
                                                    0UL
                                            ) {
                                                index + 1
                                            } else {
                                                -1
                                            }
                                        char < 128.toChar() ->
                                            if (
                                                ((1UL shl (char.code - 64)) and
                                                    0x7FFFFFFFFFFFFFFFUL) != 0UL
                                            ) {
                                                index + 1
                                            } else {
                                                -1
                                            }
                                        else -> -1
                                    }
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                parseDquote(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitQuotedString(input, startIndex, it) }
    }

    public fun parseNumVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterNumVal(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == '%') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        parseBinVal(input, index, listener).let {
                            if (it != -1) {
                                return@run it
                            }
                        }
                        parseDecVal(input, index, listener).let {
                            if (it != -1) {
                                return@run it
                            }
                        }
                        parseHexVal(input, index, listener).let {
                            if (it != -1) {
                                return@run it
                            }
                        }
                        -1
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitNumVal(input, startIndex, it) }
    }

    public fun parseBinVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterBinVal(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                when (char) {
                                    'b',
                                    'B' -> index + 1
                                    else -> -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseBit(input, index).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        if (count < 1) {
                            return@run -1
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            run {
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                run {
                                                        var index = index
                                                        run {
                                                                if (index >= input.length) {
                                                                    return@run -1
                                                                }
                                                                val char = input[index]
                                                                when {
                                                                    char < 64.toChar() ->
                                                                        if (char == '.') {
                                                                            index + 1
                                                                        } else {
                                                                            -1
                                                                        }
                                                                    char < 128.toChar() -> -1
                                                                    else -> -1
                                                                }
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        run {
                                                                var index = index
                                                                var count = 0
                                                                while (true) {
                                                                    parseBit(input, index).let {
                                                                        if (it == -1) {
                                                                            return@let false
                                                                        }
                                                                        ++count
                                                                        index = it
                                                                        true
                                                                    } || break
                                                                }
                                                                if (count < 1) {
                                                                    return@run -1
                                                                }
                                                                index
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        index
                                                    }
                                                    .let {
                                                        if (it == -1) {
                                                            return@let false
                                                        }
                                                        ++count
                                                        index = it
                                                        true
                                                    } || break
                                            }
                                            if (count < 1) {
                                                return@run -1
                                            }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    run {
                                            var index = index
                                            run {
                                                    if (index >= input.length) {
                                                        return@run -1
                                                    }
                                                    val char = input[index]
                                                    when {
                                                        char < 64.toChar() ->
                                                            if (char == '-') {
                                                                index + 1
                                                            } else {
                                                                -1
                                                            }
                                                        char < 128.toChar() -> -1
                                                        else -> -1
                                                    }
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            run {
                                                    var index = index
                                                    var count = 0
                                                    while (true) {
                                                        parseBit(input, index).let {
                                                            if (it == -1) {
                                                                return@let false
                                                            }
                                                            ++count
                                                            index = it
                                                            true
                                                        } || break
                                                    }
                                                    if (count < 1) {
                                                        return@run -1
                                                    }
                                                    index
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    -1
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitBinVal(input, startIndex, it) }
    }

    public fun parseDecVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterDecVal(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                when (char) {
                                    'd',
                                    'D' -> index + 1
                                    else -> -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseDigit(input, index).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        if (count < 1) {
                            return@run -1
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            run {
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                run {
                                                        var index = index
                                                        run {
                                                                if (index >= input.length) {
                                                                    return@run -1
                                                                }
                                                                val char = input[index]
                                                                when {
                                                                    char < 64.toChar() ->
                                                                        if (char == '.') {
                                                                            index + 1
                                                                        } else {
                                                                            -1
                                                                        }
                                                                    char < 128.toChar() -> -1
                                                                    else -> -1
                                                                }
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        run {
                                                                var index = index
                                                                var count = 0
                                                                while (true) {
                                                                    parseDigit(input, index).let {
                                                                        if (it == -1) {
                                                                            return@let false
                                                                        }
                                                                        ++count
                                                                        index = it
                                                                        true
                                                                    } || break
                                                                }
                                                                if (count < 1) {
                                                                    return@run -1
                                                                }
                                                                index
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        index
                                                    }
                                                    .let {
                                                        if (it == -1) {
                                                            return@let false
                                                        }
                                                        ++count
                                                        index = it
                                                        true
                                                    } || break
                                            }
                                            if (count < 1) {
                                                return@run -1
                                            }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    run {
                                            var index = index
                                            run {
                                                    if (index >= input.length) {
                                                        return@run -1
                                                    }
                                                    val char = input[index]
                                                    when {
                                                        char < 64.toChar() ->
                                                            if (char == '-') {
                                                                index + 1
                                                            } else {
                                                                -1
                                                            }
                                                        char < 128.toChar() -> -1
                                                        else -> -1
                                                    }
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            run {
                                                    var index = index
                                                    var count = 0
                                                    while (true) {
                                                        parseDigit(input, index).let {
                                                            if (it == -1) {
                                                                return@let false
                                                            }
                                                            ++count
                                                            index = it
                                                            true
                                                        } || break
                                                    }
                                                    if (count < 1) {
                                                        return@run -1
                                                    }
                                                    index
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    -1
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitDecVal(input, startIndex, it) }
    }

    public fun parseHexVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterHexVal(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() -> -1
                            char < 128.toChar() ->
                                when (char) {
                                    'x',
                                    'X' -> index + 1
                                    else -> -1
                                }
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseHexdig(input, index).let {
                                if (it == -1) {
                                    return@let false
                                }
                                ++count
                                index = it
                                true
                            } || break
                        }
                        if (count < 1) {
                            return@run -1
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            run {
                                    run {
                                            var index = index
                                            var count = 0
                                            while (true) {
                                                run {
                                                        var index = index
                                                        run {
                                                                if (index >= input.length) {
                                                                    return@run -1
                                                                }
                                                                val char = input[index]
                                                                when {
                                                                    char < 64.toChar() ->
                                                                        if (char == '.') {
                                                                            index + 1
                                                                        } else {
                                                                            -1
                                                                        }
                                                                    char < 128.toChar() -> -1
                                                                    else -> -1
                                                                }
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        run {
                                                                var index = index
                                                                var count = 0
                                                                while (true) {
                                                                    parseHexdig(input, index).let {
                                                                        if (it == -1) {
                                                                            return@let false
                                                                        }
                                                                        ++count
                                                                        index = it
                                                                        true
                                                                    } || break
                                                                }
                                                                if (count < 1) {
                                                                    return@run -1
                                                                }
                                                                index
                                                            }
                                                            .let {
                                                                if (it == -1) {
                                                                    return@run -1
                                                                }
                                                                index = it
                                                            }
                                                        index
                                                    }
                                                    .let {
                                                        if (it == -1) {
                                                            return@let false
                                                        }
                                                        ++count
                                                        index = it
                                                        true
                                                    } || break
                                            }
                                            if (count < 1) {
                                                return@run -1
                                            }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    run {
                                            var index = index
                                            run {
                                                    if (index >= input.length) {
                                                        return@run -1
                                                    }
                                                    val char = input[index]
                                                    when {
                                                        char < 64.toChar() ->
                                                            if (char == '-') {
                                                                index + 1
                                                            } else {
                                                                -1
                                                            }
                                                        char < 128.toChar() -> -1
                                                        else -> -1
                                                    }
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            run {
                                                    var index = index
                                                    var count = 0
                                                    while (true) {
                                                        parseHexdig(input, index).let {
                                                            if (it == -1) {
                                                                return@let false
                                                            }
                                                            ++count
                                                            index = it
                                                            true
                                                        } || break
                                                    }
                                                    if (count < 1) {
                                                        return@run -1
                                                    }
                                                    index
                                                }
                                                .let {
                                                    if (it == -1) {
                                                        return@run -1
                                                    }
                                                    index = it
                                                }
                                            index
                                        }
                                        .let {
                                            if (it != -1) {
                                                return@run it
                                            }
                                        }
                                    -1
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitHexVal(input, startIndex, it) }
    }

    public fun parseProseVal(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterProseVal(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == '<') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            run {
                                    if (index >= input.length) {
                                        return@run -1
                                    }
                                    val char = input[index]
                                    when {
                                        char < 64.toChar() ->
                                            if (
                                                ((1UL shl char.code) and 0xBFFFFFFF00000000UL) !=
                                                    0UL
                                            ) {
                                                index + 1
                                            } else {
                                                -1
                                            }
                                        char < 128.toChar() ->
                                            if (
                                                ((1UL shl (char.code - 64)) and
                                                    0x7FFFFFFFFFFFFFFFUL) != 0UL
                                            ) {
                                                index + 1
                                            } else {
                                                -1
                                            }
                                        else -> -1
                                    }
                                }
                                .let {
                                    if (it == -1) {
                                        return@let false
                                    }
                                    ++count
                                    index = it
                                    true
                                } || break
                        }
                        index
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                run {
                        if (index >= input.length) {
                            return@run -1
                        }
                        val char = input[index]
                        when {
                            char < 64.toChar() ->
                                if (char == '>') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() -> -1
                            else -> -1
                        }
                    }
                    .let {
                        if (it == -1) {
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitProseVal(input, startIndex, it) }
    }

    private fun parseAlpha(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() -> -1
                char < 128.toChar() ->
                    if (((1UL shl (char.code - 64)) and 0x7FFFFFE07FFFFFEUL) != 0UL) {
                        index + 1
                    } else {
                        -1
                    }
                else -> -1
            }
        }
    }

    private fun parseBit(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    when (char) {
                        '1',
                        '0' -> index + 1
                        else -> -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseCr(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (char == '\r') {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseCrlf(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            var index = index
            parseCr(input, index).let {
                if (it == -1) {
                    return@run -1
                }
                index = it
            }
            parseLf(input, index).let {
                if (it == -1) {
                    return@run -1
                }
                index = it
            }
            index
        }
    }

    private fun parseDigit(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (((1UL shl char.code) and 0x3FF000000000000UL) != 0UL) {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseDquote(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (char == '"') {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseHexdig(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            parseDigit(input, index).let {
                if (it != -1) {
                    return@run it
                }
            }
            run {
                    if (index >= input.length) {
                        return@run -1
                    }
                    val char = input[index]
                    when {
                        char < 64.toChar() -> -1
                        char < 128.toChar() ->
                            if (((1UL shl (char.code - 64)) and 0x7EUL) != 0UL) {
                                index + 1
                            } else {
                                -1
                            }
                        else -> -1
                    }
                }
                .let {
                    if (it != -1) {
                        return@run it
                    }
                }
            -1
        }
    }

    private fun parseHtab(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (char == '\t') {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseLf(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (char == '\n') {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseSp(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (char == ' ') {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() -> -1
                else -> -1
            }
        }
    }

    private fun parseVchar(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            if (index >= input.length) {
                return@run -1
            }
            val char = input[index]
            when {
                char < 64.toChar() ->
                    if (((1UL shl char.code) and 0xFFFFFFFE00000000UL) != 0UL) {
                        index + 1
                    } else {
                        -1
                    }
                char < 128.toChar() ->
                    if (((1UL shl (char.code - 64)) and 0x7FFFFFFFFFFFFFFFUL) != 0UL) {
                        index + 1
                    } else {
                        -1
                    }
                else -> -1
            }
        }
    }

    private fun parseWsp(input: String, startIndex: Int): Int {
        val index = startIndex
        return run {
            parseSp(input, index).let {
                if (it != -1) {
                    return@run it
                }
            }
            parseHtab(input, index).let {
                if (it != -1) {
                    return@run it
                }
            }
            -1
        }
    }

    public interface Listener {
        public fun enterRulelist(input: String, startIndex: Int) {}

        public fun exitRulelist(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRule(input: String, startIndex: Int) {}

        public fun exitRule(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRulename(input: String, startIndex: Int) {}

        public fun exitRulename(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterDefinedAs(input: String, startIndex: Int) {}

        public fun exitDefinedAs(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterElements(input: String, startIndex: Int) {}

        public fun exitElements(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterCWsp(input: String, startIndex: Int) {}

        public fun exitCWsp(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterCNl(input: String, startIndex: Int) {}

        public fun exitCNl(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterComment(input: String, startIndex: Int) {}

        public fun exitComment(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterAlternation(input: String, startIndex: Int) {}

        public fun exitAlternation(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterConcatenation(input: String, startIndex: Int) {}

        public fun exitConcatenation(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRepetition(input: String, startIndex: Int) {}

        public fun exitRepetition(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRepeat(input: String, startIndex: Int) {}

        public fun exitRepeat(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterElement(input: String, startIndex: Int) {}

        public fun exitElement(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterGroup(input: String, startIndex: Int) {}

        public fun exitGroup(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterOption(input: String, startIndex: Int) {}

        public fun exitOption(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterCharVal(input: String, startIndex: Int) {}

        public fun exitCharVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterCaseInsensitiveString(input: String, startIndex: Int) {}

        public fun exitCaseInsensitiveString(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterCaseSensitiveString(input: String, startIndex: Int) {}

        public fun exitCaseSensitiveString(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterQuotedString(input: String, startIndex: Int) {}

        public fun exitQuotedString(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterNumVal(input: String, startIndex: Int) {}

        public fun exitNumVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterBinVal(input: String, startIndex: Int) {}

        public fun exitBinVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterDecVal(input: String, startIndex: Int) {}

        public fun exitDecVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterHexVal(input: String, startIndex: Int) {}

        public fun exitHexVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterProseVal(input: String, startIndex: Int) {}

        public fun exitProseVal(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}
    }
}
