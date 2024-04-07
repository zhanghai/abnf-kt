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

data class Rule(val name: String, val element: Element) {
    init {
        requireValidName(name)
    }

    companion object {
        private val NAME_CHAR_SET =
            AsciiCharSet.of('A'..'Z') +
                AsciiCharSet.of('a'..'z') +
                AsciiCharSet.of('0'..'9') +
                AsciiCharSet.of('-')

        internal fun requireValidName(name: String) {
            for (char in name) {
                require(char in NAME_CHAR_SET) {
                    "Illegal character '$char' in rule name \"$name\""
                }
            }
        }
    }
}
