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

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State

@State(Scope.Benchmark)
class UriParserBenchmark {
    @Benchmark
    fun benchmarkGeneratedParser(): Boolean =
        GeneratedUriParser.parseUriReference(BENCHMARK_URI) == BENCHMARK_URI.length

    @Benchmark
    fun benchmarkRegexParser(): Boolean = RegexUriParser.parseUriReference(BENCHMARK_URI) != null

    companion object {
        private const val BENCHMARK_URI = "http://www.ics.uci.edu/pub/ietf/uri/#Related"
    }
}

@Suppress(
    "LABEL_NAME_CLASH",
    "MemberVisibilityCanBePrivate",
    "NAME_SHADOWING",
    "RedundantVisibilityModifier",
    "UnnecessaryVariable",
)
private object GeneratedUriParser {
    public fun parseUri(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterUri(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseScheme(input, index, listener).let {
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
                                if (char == ':') {
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
                parseHierPart(input, index, listener).let {
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
                                    var index = index
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '?') {
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
                                    parseQuery(input, index, listener).let {
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
                                                    if (char == '#') {
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
                                    parseFragment(input, index, listener).let {
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
            .also { listener?.exitUri(input, startIndex, it) }
    }

    public fun parseHierPart(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterHierPart(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        run {
                                var index = index
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseAuthority(input, index, listener).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
                        }
                        parsePathAbempty(input, index, listener).let {
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
                parsePathAbsolute(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathRootless(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathEmpty(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitHierPart(input, startIndex, it) }
    }

    public fun parseUriReference(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterUriReference(input, startIndex)
        val index = startIndex
        return run {
                parseUri(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseRelativeRef(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitUriReference(input, startIndex, it) }
    }

    public fun parseAbsoluteUri(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterAbsoluteUri(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseScheme(input, index, listener).let {
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
                                if (char == ':') {
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
                parseHierPart(input, index, listener).let {
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
                                    var index = index
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '?') {
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
                                    parseQuery(input, index, listener).let {
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
            .also { listener?.exitAbsoluteUri(input, startIndex, it) }
    }

    public fun parseRelativeRef(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRelativeRef(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseRelativePart(input, index, listener).let {
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
                                    var index = index
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == '?') {
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
                                    parseQuery(input, index, listener).let {
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
                                                    if (char == '#') {
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
                                    parseFragment(input, index, listener).let {
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
            .also { listener?.exitRelativeRef(input, startIndex, it) }
    }

    public fun parseRelativePart(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRelativePart(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        run {
                                var index = index
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseAuthority(input, index, listener).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
                        }
                        parsePathAbempty(input, index, listener).let {
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
                parsePathAbsolute(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathNoscheme(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathEmpty(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitRelativePart(input, startIndex, it) }
    }

    public fun parseScheme(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterScheme(input, startIndex)
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
                        run {
                                var index = index
                                var count = 0
                                while (true) {
                                    parseAlpha(input, index).let {
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
                                        when (char) {
                                            '.',
                                            '-',
                                            '+' -> index + 1
                                            else -> -1
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
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitScheme(input, startIndex, it) }
    }

    public fun parseAuthority(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterAuthority(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                run {
                        var index = index
                        var count = 0
                        while (count < 1) {
                            run {
                                    var index = index
                                    parseUserinfo(input, index, listener).let {
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
                                                    if (char == '@') {
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
                parseHost(input, index, listener).let {
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
                                    var index = index
                                    run {
                                            if (index >= input.length) {
                                                return@run -1
                                            }
                                            val char = input[index]
                                            when {
                                                char < 64.toChar() ->
                                                    if (char == ':') {
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
                                    parsePort(input, index, listener).let {
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
            .also { listener?.exitAuthority(input, startIndex, it) }
    }

    public fun parseUserinfo(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterUserinfo(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseUnreserved(input, index, listener).let {
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
                        if (it != -1) {
                            return@run it
                        }
                    }
                parsePctEncoded(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseSubDelims(input, index, listener).let {
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
                                if (char == ':') {
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
            .also { listener?.exitUserinfo(input, startIndex, it) }
    }

    public fun parseHost(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterHost(input, startIndex)
        val index = startIndex
        return run {
                parseIpLiteral(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseIpv4Address(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseRegName(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitHost(input, startIndex, it) }
    }

    public fun parsePort(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPort(input, startIndex)
        val index = startIndex
        return run {
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
            .also { listener?.exitPort(input, startIndex, it) }
    }

    public fun parseIpLiteral(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterIpLiteral(input, startIndex)
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
                        parseIpv6Address(input, index, listener).let {
                            if (it != -1) {
                                return@run it
                            }
                        }
                        parseIpvfuture(input, index, listener).let {
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
            .also { listener?.exitIpLiteral(input, startIndex, it) }
    }

    public fun parseIpvfuture(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterIpvfuture(input, startIndex)
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
                                    'v',
                                    'V' -> index + 1
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
                        run {
                                var index = index
                                var count = 0
                                while (true) {
                                    parseUnreserved(input, index, listener).let {
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
                        parseSubDelims(input, index, listener).let {
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
                                        if (char == ':') {
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
                            return@run -1
                        }
                        index = it
                    }
                index
            }
            .also { listener?.exitIpvfuture(input, startIndex, it) }
    }

    public fun parseIpv6Address(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterIpv6Address(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        run {
                                var index = index
                                run {
                                        var index = index
                                        var count = 0
                                        while (count < 6) {
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@let false
                                                }
                                                ++count
                                                index = it
                                                true
                                            } || break
                                        }
                                        if (count < 6) {
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
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                var index = index
                                run {
                                        var index = index
                                        var count = 0
                                        while (count < 5) {
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@let false
                                                }
                                                ++count
                                                index = it
                                                true
                                            } || break
                                        }
                                        if (count < 5) {
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
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    parseH16(input, index, listener).let {
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
                                var index = index
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                var index = index
                                run {
                                        var index = index
                                        var count = 0
                                        while (count < 4) {
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@let false
                                                }
                                                ++count
                                                index = it
                                                true
                                            } || break
                                        }
                                        if (count < 4) {
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
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 1) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                var index = index
                                run {
                                        var index = index
                                        var count = 0
                                        while (count < 3) {
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@let false
                                                }
                                                ++count
                                                index = it
                                                true
                                            } || break
                                        }
                                        if (count < 3) {
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
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 2) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        run {
                                var index = index
                                run {
                                        var index = index
                                        var count = 0
                                        while (count < 2) {
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@let false
                                                }
                                                ++count
                                                index = it
                                                true
                                            } || break
                                        }
                                        if (count < 2) {
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
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 3) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseH16(input, index, listener).let {
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
                                        if (char == ':') {
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
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 4) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseLs32(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 5) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
                                if (it == -1) {
                                    return@run -1
                                }
                                index = it
                            }
                        parseH16(input, index, listener).let {
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
                        run {
                                var index = index
                                var count = 0
                                while (count < 1) {
                                    run {
                                            var index = index
                                            parseH16(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
                                            }
                                            run {
                                                    var index = index
                                                    run {
                                                            var index = index
                                                            var count = 0
                                                            while (count < 6) {
                                                                run {
                                                                        if (index >= input.length) {
                                                                            return@run -1
                                                                        }
                                                                        val char = input[index]
                                                                        when {
                                                                            char < 64.toChar() ->
                                                                                if (char == ':') {
                                                                                    index + 1
                                                                                } else {
                                                                                    -1
                                                                                }
                                                                            char < 128.toChar() ->
                                                                                -1
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
                                                    parseH16(input, index, listener).let {
                                                        if (it == -1) {
                                                            return@run -1
                                                        }
                                                        index = it
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
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == ':') {
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
                                                if (char == ':') {
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
            .also { listener?.exitIpv6Address(input, startIndex, it) }
    }

    public fun parseH16(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterH16(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                var count = 0
                while (count < 4) {
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
            .also { listener?.exitH16(input, startIndex, it) }
    }

    public fun parseLs32(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterLs32(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        parseH16(input, index, listener).let {
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
                                        if (char == ':') {
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
                        parseH16(input, index, listener).let {
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
                parseIpv4Address(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitLs32(input, startIndex, it) }
    }

    public fun parseIpv4Address(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterIpv4Address(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseDecOctet(input, index, listener).let {
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
                parseDecOctet(input, index, listener).let {
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
                parseDecOctet(input, index, listener).let {
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
                parseDecOctet(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitIpv4Address(input, startIndex, it) }
    }

    public fun parseDecOctet(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterDecOctet(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        run {
                                var index = index
                                run {
                                        if (index >= input.length) {
                                            return@run -1
                                        }
                                        val char = input[index]
                                        when {
                                            char < 64.toChar() ->
                                                if (char == '2') {
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
                                                if (char == '5') {
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
                                        if (((1UL shl char.code) and 0x3F000000000000UL) != 0UL) {
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
                run {
                        var index = index
                        run {
                                if (index >= input.length) {
                                    return@run -1
                                }
                                val char = input[index]
                                when {
                                    char < 64.toChar() ->
                                        if (char == '2') {
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
                                        if (((1UL shl char.code) and 0x1F000000000000UL) != 0UL) {
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
                        parseDigit(input, index).let {
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
                        run {
                                if (index >= input.length) {
                                    return@run -1
                                }
                                val char = input[index]
                                when {
                                    char < 64.toChar() ->
                                        if (char == '1') {
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
                                while (count < 2) {
                                    parseDigit(input, index).let {
                                        if (it == -1) {
                                            return@let false
                                        }
                                        ++count
                                        index = it
                                        true
                                    } || break
                                }
                                if (count < 2) {
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
                run {
                        var index = index
                        run {
                                if (index >= input.length) {
                                    return@run -1
                                }
                                val char = input[index]
                                when {
                                    char < 64.toChar() ->
                                        if (((1UL shl char.code) and 0x3FE000000000000UL) != 0UL) {
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
                        parseDigit(input, index).let {
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
                parseDigit(input, index).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitDecOctet(input, startIndex, it) }
    }

    public fun parseRegName(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterRegName(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseUnreserved(input, index, listener).let {
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
                        if (it != -1) {
                            return@run it
                        }
                    }
                parsePctEncoded(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseSubDelims(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitRegName(input, startIndex, it) }
    }

    public fun parsePath(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPath(input, startIndex)
        val index = startIndex
        return run {
                parsePathAbempty(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathAbsolute(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathNoscheme(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathRootless(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePathEmpty(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitPath(input, startIndex, it) }
    }

    public fun parsePathAbempty(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPathAbempty(input, startIndex)
        val index = startIndex
        return run {
                var index = index
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
                parseSegment(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitPathAbempty(input, startIndex, it) }
    }

    public fun parsePathAbsolute(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPathAbsolute(input, startIndex)
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
                        while (count < 1) {
                            run {
                                    var index = index
                                    parseSegmentNz(input, index, listener).let {
                                        if (it == -1) {
                                            return@run -1
                                        }
                                        index = it
                                    }
                                    run {
                                            var index = index
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
                                            parseSegment(input, index, listener).let {
                                                if (it == -1) {
                                                    return@run -1
                                                }
                                                index = it
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
            .also { listener?.exitPathAbsolute(input, startIndex, it) }
    }

    public fun parsePathNoscheme(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPathNoscheme(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseSegmentNzNc(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
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
                        parseSegment(input, index, listener).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
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
            .also { listener?.exitPathNoscheme(input, startIndex, it) }
    }

    public fun parsePathRootless(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPathRootless(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                parseSegmentNz(input, index, listener).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                run {
                        var index = index
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
                        parseSegment(input, index, listener).let {
                            if (it == -1) {
                                return@run -1
                            }
                            index = it
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
            .also { listener?.exitPathRootless(input, startIndex, it) }
    }

    public fun parsePathEmpty(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPathEmpty(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                var count = 0
                while (count < 0) {
                    run {
                            if (index >= input.length) {
                                return@run -1
                            }
                            val char = input[index]
                            when {
                                char < 64.toChar() -> -1
                                char < 128.toChar() -> -1
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
            .also { listener?.exitPathEmpty(input, startIndex, it) }
    }

    public fun parseSegment(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterSegment(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                var count = 0
                while (true) {
                    parsePchar(input, index, listener).let {
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
            .also { listener?.exitSegment(input, startIndex, it) }
    }

    public fun parseSegmentNz(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterSegmentNz(input, startIndex)
        val index = startIndex
        return run {
                var index = index
                var count = 0
                while (true) {
                    parsePchar(input, index, listener).let {
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
            .also { listener?.exitSegmentNz(input, startIndex, it) }
    }

    public fun parseSegmentNzNc(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterSegmentNzNc(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parseUnreserved(input, index, listener).let {
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
                parsePctEncoded(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseSubDelims(input, index, listener).let {
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
                                if (char == '@') {
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
            .also { listener?.exitSegmentNzNc(input, startIndex, it) }
    }

    public fun parsePchar(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPchar(input, startIndex)
        val index = startIndex
        return run {
                parseUnreserved(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parsePctEncoded(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseSubDelims(input, index, listener).let {
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
                                if (char == ':') {
                                    index + 1
                                } else {
                                    -1
                                }
                            char < 128.toChar() ->
                                if (char == '@') {
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
            .also { listener?.exitPchar(input, startIndex, it) }
    }

    public fun parseQuery(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterQuery(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parsePchar(input, index, listener).let {
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
                                when (char) {
                                    '?',
                                    '/' -> index + 1
                                    else -> -1
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
            .also { listener?.exitQuery(input, startIndex, it) }
    }

    public fun parseFragment(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterFragment(input, startIndex)
        val index = startIndex
        return run {
                run {
                        var index = index
                        var count = 0
                        while (true) {
                            parsePchar(input, index, listener).let {
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
                                when (char) {
                                    '?',
                                    '/' -> index + 1
                                    else -> -1
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
            .also { listener?.exitFragment(input, startIndex, it) }
    }

    public fun parsePctEncoded(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterPctEncoded(input, startIndex)
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
                parseHexdig(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                parseHexdig(input, index).let {
                    if (it == -1) {
                        return@run -1
                    }
                    index = it
                }
                index
            }
            .also { listener?.exitPctEncoded(input, startIndex, it) }
    }

    public fun parseUnreserved(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterUnreserved(input, startIndex)
        val index = startIndex
        return run {
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
                                when (char) {
                                    '.',
                                    '-' -> index + 1
                                    else -> -1
                                }
                            char < 128.toChar() ->
                                when (char) {
                                    '~',
                                    '_' -> index + 1
                                    else -> -1
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
            .also { listener?.exitUnreserved(input, startIndex, it) }
    }

    public fun parseReserved(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterReserved(input, startIndex)
        val index = startIndex
        return run {
                parseGenDelims(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                parseSubDelims(input, index, listener).let {
                    if (it != -1) {
                        return@run it
                    }
                }
                -1
            }
            .also { listener?.exitReserved(input, startIndex, it) }
    }

    public fun parseGenDelims(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterGenDelims(input, startIndex)
        val index = startIndex
        return run {
                if (index >= input.length) {
                    return@run -1
                }
                val char = input[index]
                when {
                    char < 64.toChar() ->
                        if (((1UL shl char.code) and 0x8400800800000000UL) != 0UL) {
                            index + 1
                        } else {
                            -1
                        }
                    char < 128.toChar() ->
                        when (char) {
                            ']',
                            '[',
                            '@' -> index + 1
                            else -> -1
                        }
                    else -> -1
                }
            }
            .also { listener?.exitGenDelims(input, startIndex, it) }
    }

    public fun parseSubDelims(
        input: String,
        startIndex: Int = 0,
        listener: Listener? = null,
    ): Int {
        listener?.enterSubDelims(input, startIndex)
        val index = startIndex
        return run {
                if (index >= input.length) {
                    return@run -1
                }
                val char = input[index]
                when {
                    char < 64.toChar() ->
                        if (((1UL shl char.code) and 0x28001FD200000000UL) != 0UL) {
                            index + 1
                        } else {
                            -1
                        }
                    char < 128.toChar() -> -1
                    else -> -1
                }
            }
            .also { listener?.exitSubDelims(input, startIndex, it) }
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

    public interface Listener {
        public fun enterUri(input: String, startIndex: Int) {}

        public fun exitUri(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterHierPart(input: String, startIndex: Int) {}

        public fun exitHierPart(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterUriReference(input: String, startIndex: Int) {}

        public fun exitUriReference(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterAbsoluteUri(input: String, startIndex: Int) {}

        public fun exitAbsoluteUri(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRelativeRef(input: String, startIndex: Int) {}

        public fun exitRelativeRef(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRelativePart(input: String, startIndex: Int) {}

        public fun exitRelativePart(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterScheme(input: String, startIndex: Int) {}

        public fun exitScheme(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterAuthority(input: String, startIndex: Int) {}

        public fun exitAuthority(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterUserinfo(input: String, startIndex: Int) {}

        public fun exitUserinfo(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterHost(input: String, startIndex: Int) {}

        public fun exitHost(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPort(input: String, startIndex: Int) {}

        public fun exitPort(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterIpLiteral(input: String, startIndex: Int) {}

        public fun exitIpLiteral(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterIpvfuture(input: String, startIndex: Int) {}

        public fun exitIpvfuture(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterIpv6Address(input: String, startIndex: Int) {}

        public fun exitIpv6Address(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterH16(input: String, startIndex: Int) {}

        public fun exitH16(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterLs32(input: String, startIndex: Int) {}

        public fun exitLs32(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterIpv4Address(input: String, startIndex: Int) {}

        public fun exitIpv4Address(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterDecOctet(input: String, startIndex: Int) {}

        public fun exitDecOctet(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterRegName(input: String, startIndex: Int) {}

        public fun exitRegName(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPath(input: String, startIndex: Int) {}

        public fun exitPath(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPathAbempty(input: String, startIndex: Int) {}

        public fun exitPathAbempty(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPathAbsolute(input: String, startIndex: Int) {}

        public fun exitPathAbsolute(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPathNoscheme(input: String, startIndex: Int) {}

        public fun exitPathNoscheme(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPathRootless(input: String, startIndex: Int) {}

        public fun exitPathRootless(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPathEmpty(input: String, startIndex: Int) {}

        public fun exitPathEmpty(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterSegment(input: String, startIndex: Int) {}

        public fun exitSegment(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterSegmentNz(input: String, startIndex: Int) {}

        public fun exitSegmentNz(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterSegmentNzNc(input: String, startIndex: Int) {}

        public fun exitSegmentNzNc(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPchar(input: String, startIndex: Int) {}

        public fun exitPchar(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterQuery(input: String, startIndex: Int) {}

        public fun exitQuery(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterFragment(input: String, startIndex: Int) {}

        public fun exitFragment(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterPctEncoded(input: String, startIndex: Int) {}

        public fun exitPctEncoded(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterUnreserved(input: String, startIndex: Int) {}

        public fun exitUnreserved(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterReserved(input: String, startIndex: Int) {}

        public fun exitReserved(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterGenDelims(input: String, startIndex: Int) {}

        public fun exitGenDelims(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}

        public fun enterSubDelims(input: String, startIndex: Int) {}

        public fun exitSubDelims(
            input: String,
            startIndex: Int,
            endIndex: Int,
        ) {}
    }
}

private object RegexUriParser {
    private const val GEN_DELIMS = "[:/?#\\[\\]@]"
    private const val SUB_DELIMS = "[!$&\"()*+,;=]"
    private const val UNRESERVED = "[a-zA-Z0-9\\-._~]"
    private const val RESERVED = "($GEN_DELIMS|$SUB_DELIMS)"
    private const val PCT_ENCODED = "%[0-9A-Fa-f][0-9A-Fa-f]"
    private const val SCHEME = "[a-zA-Z][a-zA-Z0-9+\\-.]*"
    private const val USERINFO = "($UNRESERVED|$PCT_ENCODED|$SUB_DELIMS|:)*"
    private const val DEC_OCTET = "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])"
    private const val IPV4ADDRESS = "$DEC_OCTET\\.$DEC_OCTET\\.$DEC_OCTET\\.$DEC_OCTET"
    private const val H16 = "[0-9A-Fa-f]{1,4}"
    private const val LS32 = "($H16:$H16|$IPV4ADDRESS)"
    private const val IPV6ADDRESS =
        "(($H16:){6}$LS32|" +
            "::($H16:){5}$LS32|" +
            "($H16)?::($H16:){4}$LS32|" +
            "(($H16:)?$H16)?::($H16:){3}$LS32|" +
            "(($H16:){0,2}$H16)?::($H16:){2}$LS32|" +
            "(($H16:){0,3}$H16)?::$H16:$LS32|(($H16:){0,4}$H16)?::$LS32|" +
            "(($H16:){0,5}$H16)?::$H16|(($H16:){0,6}$H16)?::)"
    private const val IPVFUTURE = "[vV][0-9A-Fa-f]+\\.($UNRESERVED|$SUB_DELIMS|:)+"
    private const val IP_LITERAL = "\\[($IPV6ADDRESS|$IPVFUTURE)\\]"
    private const val REG_NAME = "($UNRESERVED|$PCT_ENCODED|$SUB_DELIMS)*"
    private const val HOST = "($IP_LITERAL|$IPV4ADDRESS|$REG_NAME)"
    private const val PORT = "[0-9]*"
    private const val AUTHORITY = "($USERINFO@)?$HOST(:$PORT)?"
    private const val PCHAR = "($UNRESERVED|$PCT_ENCODED|$SUB_DELIMS|[:@])"
    private const val SEGMENT = "($PCHAR)*"
    private const val SEGMENT_NZ = "($PCHAR)+"
    private const val SEGMENT_NZ_NC = "($UNRESERVED|$PCT_ENCODED|$SUB_DELIMS|@)+"
    private const val PATH_ABEMPTY = "(/$SEGMENT)*"
    private const val PATH_ABSOLUTE = "/($SEGMENT_NZ(/$SEGMENT)*)?"
    private const val PATH_NOSCHEME = "$SEGMENT_NZ_NC(/$SEGMENT)*"
    private const val PATH_ROOTLESS = "$SEGMENT_NZ(/$SEGMENT)*"
    private const val PATH_EMPTY = "($PCHAR){0}"
    private const val PATH =
        "($PATH_ABEMPTY|$PATH_ABSOLUTE|$PATH_NOSCHEME|$PATH_ROOTLESS|$PATH_EMPTY)"
    private const val HIER_PART =
        "(//$AUTHORITY$PATH_ABEMPTY|$PATH_ABSOLUTE|$PATH_ROOTLESS|$PATH_EMPTY)"
    private const val RELATIVE_PART =
        "(//$AUTHORITY$PATH_ABEMPTY|$PATH_ABSOLUTE|$PATH_NOSCHEME|$PATH_EMPTY)"
    private const val QUERY = "($PCHAR|[/?])*"
    private const val FRAGMENT = "($PCHAR|[/?])*"
    private const val ABSOLUTE_URI = "$SCHEME:$HIER_PART(\\?$QUERY)?"
    private const val URI = "$SCHEME:$HIER_PART(\\?$QUERY)?(#$FRAGMENT)?"
    private const val RELATIVE_REF = "$RELATIVE_PART(\\?$QUERY)?(#$FRAGMENT)?"
    private const val URI_REFERENCE = "($URI|$RELATIVE_REF)"

    private val URI_REFERENCE_REGEX = Regex(URI_REFERENCE)

    fun parseUriReference(input: String): MatchResult? = URI_REFERENCE_REGEX.matchEntire(input)
}
