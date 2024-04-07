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

// https://datatracker.ietf.org/doc/html/rfc5234#section-4
// https://datatracker.ietf.org/doc/html/rfc7405#section-2.2
internal val AbnfRules =
    listOf(
        Rule(
            "rulelist",
            Repetition.ofAtLeast(
                1,
                Alternation(
                    RuleName("rule"),
                    Concatenation(Repetition.ofAny(RuleName("c-wsp")), RuleName("c-nl"))
                )
            )
        ),
        Rule(
            "rule",
            Concatenation(
                RuleName("rulename"),
                RuleName("defined-as"),
                RuleName("elements"),
                RuleName("c-nl")
            )
        ),
        Rule(
            "rulename",
            Concatenation(
                RuleName("ALPHA"),
                Repetition.ofAny(
                    Alternation(RuleName("ALPHA"), RuleName("DIGIT"), TerminalValue.of('-'))
                )
            )
        ),
        Rule(
            "defined-as",
            Concatenation(
                Repetition.ofAny(RuleName("c-wsp")),
                Alternation(
                    TerminalValue.of('='),
                    Concatenation(TerminalValue.of('='), TerminalValue.of('/'))
                ),
                Repetition.ofAny(RuleName("c-wsp"))
            )
        ),
        Rule(
            "elements",
            Concatenation(RuleName("alternation"), Repetition.ofAny(RuleName("c-wsp")))
        ),
        Rule(
            "c-wsp",
            Alternation(RuleName("WSP"), Concatenation(RuleName("c-nl"), RuleName("WSP")))
        ),
        Rule("c-nl", Alternation(RuleName("comment"), RuleName("CRLF"))),
        Rule(
            "comment",
            Concatenation(
                TerminalValue.of(';'),
                Repetition.ofAny(Alternation(RuleName("WSP"), RuleName("VCHAR"))),
                RuleName("CRLF")
            )
        ),
        Rule(
            "alternation",
            Concatenation(
                RuleName("concatenation"),
                Repetition.ofAny(
                    Concatenation(
                        Repetition.ofAny(RuleName("c-wsp")),
                        TerminalValue.of('/'),
                        Repetition.ofAny(RuleName("c-wsp")),
                        RuleName("concatenation")
                    )
                )
            )
        ),
        Rule(
            "concatenation",
            Concatenation(
                RuleName("repetition"),
                Repetition.ofAny(
                    Concatenation(
                        Repetition.ofAtLeast(1, RuleName("c-wsp")),
                        RuleName("repetition")
                    )
                )
            )
        ),
        Rule(
            "repetition",
            Concatenation(Repetition.ofOptional(RuleName("repeat")), RuleName("element"))
        ),
        Rule(
            "repeat",
            // Reordered for "first-match-wins".
            // Alternation(
            //     Repetition.ofAtLeast(1, RuleName("DIGIT")),
            //     Concatenation(
            //         Repetition.ofAny(RuleName("DIGIT")),
            //         TerminalValue.of('*'),
            //         Repetition.ofAny(RuleName("DIGIT"))
            //     )
            // )
            Alternation(
                Concatenation(
                    Repetition.ofAny(RuleName("DIGIT")),
                    TerminalValue.of('*'),
                    Repetition.ofAny(RuleName("DIGIT"))
                ),
                Repetition.ofAtLeast(1, RuleName("DIGIT"))
            )
        ),
        Rule(
            "element",
            Alternation(
                RuleName("rulename"),
                RuleName("group"),
                RuleName("option"),
                RuleName("char-val"),
                RuleName("num-val"),
                RuleName("prose-val")
            )
        ),
        Rule(
            "group",
            Concatenation(
                TerminalValue.of('('),
                Repetition.ofAny(RuleName("c-wsp")),
                RuleName("alternation"),
                Repetition.ofAny(RuleName("c-wsp")),
                TerminalValue.of(')'),
            )
        ),
        Rule(
            "option",
            Concatenation(
                TerminalValue.of('['),
                Repetition.ofAny(RuleName("c-wsp")),
                RuleName("alternation"),
                Repetition.ofAny(RuleName("c-wsp")),
                TerminalValue.of(']'),
            )
        ),
        Rule(
            "char-val",
            Alternation(RuleName("case-insensitive-string"), RuleName("case-sensitive-string"))
        ),
        Rule(
            "case-insensitive-string",
            Concatenation(
                Repetition.ofOptional(
                    Concatenation(TerminalValue.of('%'), TerminalValue.of('I', 'i'))
                ),
                RuleName("quoted-string")
            )
        ),
        Rule(
            "case-sensitive-string",
            Concatenation(
                TerminalValue.of('%'),
                TerminalValue.of('S', 's'),
                RuleName("quoted-string")
            )
        ),
        Rule(
            "quoted-string",
            Concatenation(
                RuleName("DQUOTE"),
                Repetition.ofAny(
                    Alternation(
                        TerminalValue.of('\u0020'..'\u0021'),
                        TerminalValue.of('\u0023'..'\u007E')
                    )
                ),
                RuleName("DQUOTE")
            )
        ),
        Rule(
            "num-val",
            Concatenation(
                TerminalValue.of('%'),
                Alternation(RuleName("bin-val"), RuleName("dec-val"), RuleName("hex-val"))
            )
        ),
        Rule(
            "bin-val",
            Concatenation(
                TerminalValue.of('B', 'b'),
                Repetition.ofAtLeast(1, RuleName("BIT")),
                Repetition.ofOptional(
                    Alternation(
                        Repetition.ofAtLeast(
                            1,
                            Concatenation(
                                TerminalValue.of('.'),
                                Repetition.ofAtLeast(1, RuleName("BIT"))
                            )
                        ),
                        Concatenation(
                            TerminalValue.of('-'),
                            Repetition.ofAtLeast(1, RuleName("BIT"))
                        )
                    )
                )
            )
        ),
        Rule(
            "dec-val",
            Concatenation(
                TerminalValue.of('D', 'd'),
                Repetition.ofAtLeast(1, RuleName("DIGIT")),
                Repetition.ofOptional(
                    Alternation(
                        Repetition.ofAtLeast(
                            1,
                            Concatenation(
                                TerminalValue.of('.'),
                                Repetition.ofAtLeast(1, RuleName("DIGIT"))
                            )
                        ),
                        Concatenation(
                            TerminalValue.of('-'),
                            Repetition.ofAtLeast(1, RuleName("DIGIT"))
                        )
                    )
                )
            )
        ),
        Rule(
            "hex-val",
            Concatenation(
                TerminalValue.of('X', 'x'),
                Repetition.ofAtLeast(1, RuleName("HEXDIG")),
                Repetition.ofOptional(
                    Alternation(
                        Repetition.ofAtLeast(
                            1,
                            Concatenation(
                                TerminalValue.of('.'),
                                Repetition.ofAtLeast(1, RuleName("HEXDIG"))
                            )
                        ),
                        Concatenation(
                            TerminalValue.of('-'),
                            Repetition.ofAtLeast(1, RuleName("HEXDIG"))
                        )
                    )
                )
            )
        ),
        Rule(
            "prose-val",
            Concatenation(
                TerminalValue.of('<'),
                Repetition.ofAny(
                    Alternation(
                        TerminalValue.of('\u0020'..'\u003D'),
                        TerminalValue.of('\u003F'..'\u007E')
                    )
                ),
                TerminalValue.of('>')
            )
        )
    )
