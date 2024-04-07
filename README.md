# abnf-kt

[![JVM CI status](https://github.com/zhanghai/abnf-kt/workflows/JVM%20CI/badge.svg)](https://github.com/zhanghai/abnf-kt/actions) [![GitHub release](https://img.shields.io/github/v/release/zhanghai/abnf-kt)](https://github.com/zhanghai/abnf-kt/releases) [![License](https://img.shields.io/github/license/zhanghai/abnf-kt?color=blue)](LICENSE)

ABNF parser generator for Kotlin.

This is not an officially supported Google product.

## Design

Augmented BNF (ABNF) is a modified version of Backus-Naur Form (BNF) often used by Internet technical specifications to define a formal syntax. It is documented in [RFC 5234](https://datatracker.ietf.org/doc/html/rfc5234) and extended by [RFC 7405](https://datatracker.ietf.org/doc/html/rfc7405).

This tool generates a recursive-descent parser from an ABNF grammar, and the generated parser is optimized for speed and zero allocations. The generated parser performed around 40x faster compared to regular expressions in a [benchmark](src/test/kotlin/UriParserBenchmark.kt) on desktop JVM.

> [!WARNING]
> This tool may require modifications to your ABNF rules, or the generated parser may incorrectly reject valid input - see below.

Due to recursive-descent parsing, the alternative operator in ABNF is implemented as always selecting the first match (like in PEG), which is favored for simplicity and performance.

Despite that RFC 5234 didn't explicitly specify the expected behavior for the alternative operator, the `reepat` rule ([rejected errata](https://www.rfc-editor.org/errata/eid1423)) in [ABNF grammar](https://datatracker.ietf.org/doc/html/rfc5234#section-4), as well as the `dec-octet` rule ([rejected errata](https://www.rfc-editor.org/errata/eid4393)) and the `IPv6address` rule ([rejected errata](https://www.rfc-editor.org/errata/eid4394)) in [URI grammar](https://datatracker.ietf.org/doc/html/rfc3986#appendix-A), all indicated that the alternative operator is in fact not always selecting the first match (despite rules may be re-written to satisfy that).

There are also cases where a grammar explicitly specifies a "first-match-wins" rule (e.g. the [URI grammar](https://datatracker.ietf.org/doc/html/rfc3986#section-4.1)), but it is only used in case of ambiguity and doesn't always mean one can always select the first match (which may lead to a parse failure that could be successful if a later match is selected).

As a result, certain ABNF rules may need to be modified to always select the first match, otherwise the generated parser may incorrectly reject valid input.

## Usage

```bash
abnf-kt PACKAGE_NAME CLASS_NAME [FILE]
```

When `FILE` is absent, `abnf-kt` reads from standard input.

## License

    Copyright 2024 Google LLC

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        https://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
