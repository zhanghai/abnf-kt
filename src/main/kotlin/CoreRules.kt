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

// https://datatracker.ietf.org/doc/html/rfc5234#appendix-B.1
val CoreRules =
    listOf(
        Rule("ALPHA", Alternation(TerminalValue.of('A'..'Z'), TerminalValue.of('a'..'z'))),
        Rule("BIT", TerminalValue.of('0', '1')),
        Rule("CHAR", TerminalValue.of('\u0001'..'\u007F')),
        Rule("CR", TerminalValue.of('\r')),
        Rule("CRLF", Concatenation(RuleName("CR"), RuleName("LF"))),
        Rule("CTL", Alternation(TerminalValue.of('\u0000'..'\u001F'), TerminalValue.of('\u007F'))),
        Rule("DIGIT", TerminalValue.of('0'..'9')),
        Rule("DQUOTE", TerminalValue.of('"')),
        Rule("HEXDIG", Alternation(RuleName("DIGIT"), TerminalValue.of('A'..'F'))),
        Rule("HTAB", TerminalValue.of('\t')),
        Rule("LF", TerminalValue.of('\n')),
        Rule(
            "LWSP",
            Repetition.ofAny(
                Alternation(RuleName("WSP"), Concatenation(RuleName("CRLF"), RuleName("WSP")))
            )
        ),
        Rule("OCTET", TerminalValue.of('\u0000'..'\u00FF')),
        Rule("SP", TerminalValue.of(' ')),
        Rule("VCHAR", TerminalValue.of('\u0021'..'\u007E')),
        Rule("WSP", Alternation(RuleName("SP"), RuleName("HTAB")))
    )
