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

import kotlin.test.Test

class AbnfParserTest {
    @Test
    fun testParseAbnfRules() {
        AbnfParser.parse(AbnfRulesText)
    }

    @Test
    fun testParseUriRules() {
        AbnfParser.parse(UriRulesText)
    }

    companion object {
        private val AbnfRulesText =
            """
                rulelist       =  1*( rule / (*c-wsp c-nl) )

                rule           =  rulename defined-as elements c-nl
                                       ; continues if next line starts
                                       ;  with white space

                rulename       =  ALPHA *(ALPHA / DIGIT / "-")

                defined-as     =  *c-wsp ("=" / "=/") *c-wsp
                                       ; basic rules definition and
                                       ;  incremental alternatives

                elements       =  alternation *c-wsp

                c-wsp          =  WSP / (c-nl WSP)

                c-nl           =  comment / CRLF
                                       ; comment or newline

                comment        =  ";" *(WSP / VCHAR) CRLF

                alternation    =  concatenation
                                  *(*c-wsp "/" *c-wsp concatenation)

                concatenation  =  repetition *(1*c-wsp repetition)

                repetition     =  [repeat] element

                ; Reordered for "first-match-wins".
                ; repeat         =  1*DIGIT / (*DIGIT "*" *DIGIT)
                repeat         =  (*DIGIT "*" *DIGIT) / 1*DIGIT

                element        =  rulename / group / option /
                                  char-val / num-val / prose-val

                group          =  "(" *c-wsp alternation *c-wsp ")"

                option         =  "[" *c-wsp alternation *c-wsp "]"

                char-val       =  case-insensitive-string /
                                  case-sensitive-string

                case-insensitive-string =
                                  [ "%i" ] quoted-string

                case-sensitive-string =
                                  "%s" quoted-string

                quoted-string  =  DQUOTE *(%x20-21 / %x23-7E) DQUOTE
                                       ; quoted string of SP and VCHAR
                                       ;  without DQUOTE

                num-val        =  "%" (bin-val / dec-val / hex-val)

                bin-val        =  "b" 1*BIT
                                  [ 1*("." 1*BIT) / ("-" 1*BIT) ]
                                       ; series of concatenated bit values
                                       ;  or single ONEOF range

                dec-val        =  "d" 1*DIGIT
                                  [ 1*("." 1*DIGIT) / ("-" 1*DIGIT) ]

                hex-val        =  "x" 1*HEXDIG
                                  [ 1*("." 1*HEXDIG) / ("-" 1*HEXDIG) ]

                prose-val      =  "<" *(%x20-3D / %x3F-7E) ">"
                                       ; bracketed string of SP and VCHAR
                                       ;  without angles
                                       ; prose description, to be used as
                                       ;  last resort
            """
                .trimIndent()
                .replace("\n", "\r\n") + "\r\n"

        private val UriRulesText =
            """
                URI           = scheme ":" hier-part [ "?" query ] [ "#" fragment ]

                hier-part     = "//" authority path-abempty
                              / path-absolute
                              / path-rootless
                              / path-empty

                URI-reference = URI / relative-ref

                absolute-URI  = scheme ":" hier-part [ "?" query ]

                relative-ref  = relative-part [ "?" query ] [ "#" fragment ]

                relative-part = "//" authority path-abempty
                              / path-absolute
                              / path-noscheme
                              / path-empty

                scheme        = ALPHA *( ALPHA / DIGIT / "+" / "-" / "." )

                authority     = [ userinfo "@" ] host [ ":" port ]
                userinfo      = *( unreserved / pct-encoded / sub-delims / ":" )
                host          = IP-literal / IPv4address / reg-name
                port          = *DIGIT

                IP-literal    = "[" ( IPv6address / IPvFuture  ) "]"

                IPvFuture     = "v" 1*HEXDIG "." 1*( unreserved / sub-delims / ":" )

                ; Rewritten for "first-match-wins".
                ; IPv6address   =                            6( h16 ":" ) ls32
                ;               /                       "::" 5( h16 ":" ) ls32
                ;               / [               h16 ] "::" 4( h16 ":" ) ls32
                ;               / [ *1( h16 ":" ) h16 ] "::" 3( h16 ":" ) ls32
                ;               / [ *2( h16 ":" ) h16 ] "::" 2( h16 ":" ) ls32
                ;               / [ *3( h16 ":" ) h16 ] "::"    h16 ":"   ls32
                ;               / [ *4( h16 ":" ) h16 ] "::"              ls32
                ;               / [ *5( h16 ":" ) h16 ] "::"              h16
                ;               / [ *6( h16 ":" ) h16 ] "::"
                IPv6address   =                            6( h16 ":" ) ls32
                              /                       "::" 5( h16 ":" ) ls32
                              / [               h16 ] "::" 4( h16 ":" ) ls32
                              / [ h16 *1( ":" h16 ) ] "::" 3( h16 ":" ) ls32
                              / [ h16 *2( ":" h16 ) ] "::" 2( h16 ":" ) ls32
                              / [ h16 *3( ":" h16 ) ] "::"    h16 ":"   ls32
                              / [ h16 *4( ":" h16 ) ] "::"              ls32
                              / [ h16 *5( ":" h16 ) ] "::"              h16
                              / [ h16 *6( ":" h16 ) ] "::"

                h16           = 1*4HEXDIG
                ls32          = ( h16 ":" h16 ) / IPv4address
                IPv4address   = dec-octet "." dec-octet "." dec-octet "." dec-octet

                ; Reordered for "first-match-wins".
                ; dec-octet     = DIGIT                 ; 0-9
                ;               / %x31-39 DIGIT         ; 10-99
                ;               / "1" 2DIGIT            ; 100-199
                ;               / "2" %x30-34 DIGIT     ; 200-249
                ;               / "25" %x30-35          ; 250-255
                dec-octet     = "25" %x30-35          ; 250-255
                              / "2" %x30-34 DIGIT     ; 200-249
                              / "1" 2DIGIT            ; 100-199
                              / %x31-39 DIGIT         ; 10-99
                              / DIGIT                 ; 0-9

                reg-name      = *( unreserved / pct-encoded / sub-delims )

                path          = path-abempty    ; begins with "/" or is empty
                              / path-absolute   ; begins with "/" but not "//"
                              / path-noscheme   ; begins with a non-colon segment
                              / path-rootless   ; begins with a segment
                              / path-empty      ; zero characters

                path-abempty  = *( "/" segment )
                path-absolute = "/" [ segment-nz *( "/" segment ) ]
                path-noscheme = segment-nz-nc *( "/" segment )
                path-rootless = segment-nz *( "/" segment )
                path-empty    = 0<pchar>

                segment       = *pchar
                segment-nz    = 1*pchar
                segment-nz-nc = 1*( unreserved / pct-encoded / sub-delims / "@" )
                              ; non-zero-length segment without any colon ":"

                pchar         = unreserved / pct-encoded / sub-delims / ":" / "@"

                query         = *( pchar / "/" / "?" )

                fragment      = *( pchar / "/" / "?" )

                pct-encoded   = "%" HEXDIG HEXDIG

                unreserved    = ALPHA / DIGIT / "-" / "." / "_" / "~"
                reserved      = gen-delims / sub-delims
                gen-delims    = ":" / "/" / "?" / "#" / "[" / "]" / "@"
                sub-delims    = "!" / "${'$'}" / "&" / "'" / "(" / ")"
                              / "*" / "+" / "," / ";" / "="
            """
                .trimIndent()
                .replace("\n", "\r\n") + "\r\n"
    }
}
