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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock

object ParserGenerator {
    fun generate(packageName: String, name: String, kdoc: String?, rules: List<Rule>): String {
        val ruleNames = rules.mapTo(mutableSetOf()) { it.name }
        require(rules.size == ruleNames.size) { "Duplicate rule name" }
        val coreRuleNames = CoreRules.mapTo(mutableSetOf()) { it.name }
        val allRuleNames = ruleNames + coreRuleNames
        val referencedRuleNames = collectRuleNames(rules, rules + CoreRules)
        require(allRuleNames.containsAll(referencedRuleNames)) { "Unknown rule name" }

        val optimizedRules = rules.map { RuleOptimizer.optimize(it) }
        val optimizedCoreRules = CoreRules.map { RuleOptimizer.optimize(it) }
        val optimizedReferencedRuleNames =
            collectRuleNames(optimizedRules, optimizedRules + optimizedCoreRules)
        val optimizedReferencedCoreRules =
            optimizedCoreRules.filter { it.name in optimizedReferencedRuleNames }
        val optimizedAllRules = optimizedRules + optimizedReferencedCoreRules

        val className = ClassName(packageName, name)
        return FileSpec.builder(className)
            .indent("    ")
            .addKotlinDefaultImports()
            .addType(generateObject(className, kdoc, optimizedAllRules, ruleNames))
            .build()
            .toString()
    }

    private fun collectRuleNames(rules: List<Rule>, allRules: List<Rule>): Set<String> {
        val ruleNames = mutableSetOf<String>()
        val allRulesMap = allRules.associateBy { it.name }
        for (rule in rules) {
            if (rule.name in ruleNames) {
                continue
            }
            collectRuleNames(rule.element, allRulesMap, ruleNames)
        }
        return ruleNames
    }

    private fun collectRuleNames(
        element: Element,
        allRules: Map<String, Rule>,
        ruleNames: MutableSet<String>
    ) {
        when (element) {
            is Alternation -> element.elements.forEach { collectRuleNames(it, allRules, ruleNames) }
            is Concatenation ->
                element.elements.forEach { collectRuleNames(it, allRules, ruleNames) }
            is Repetition -> collectRuleNames(element.element, allRules, ruleNames)
            is RuleName -> {
                val isNewRuleName = ruleNames.add(element.name)
                if (isNewRuleName) {
                    allRules[element.name]?.let {
                        collectRuleNames(it.element, allRules, ruleNames)
                    }
                }
            }
            is TerminalValue -> {}
        }
    }

    private fun generateObject(
        className: ClassName,
        kdoc: String?,
        rules: List<Rule>,
        publicRuleNames: Set<String>
    ): TypeSpec =
        TypeSpec.objectBuilder(className)
            .apply {
                if (kdoc != null) {
                    addKdoc("%L", kdoc)
                }
            }
            .addAnnotation(
                AnnotationSpec.builder(Suppress::class)
                    .addMember("%S", "LABEL_NAME_CLASH")
                    .addMember("%S", "MemberVisibilityCanBePrivate")
                    .addMember("%S", "NAME_SHADOWING")
                    .addMember("%S", "RedundantVisibilityModifier")
                    .addMember("%S", "UnnecessaryVariable")
                    .build()
            )
            .apply {
                val listenerType = className.nestedClass("Listener")
                rules.forEach {
                    addFunction(generateParseFunction(it, publicRuleNames, listenerType))
                }
                addType(generateListenerInterface(publicRuleNames))
            }
            .build()

    private fun generateParseFunction(
        rule: Rule,
        publicRuleNames: Set<String>,
        listenerType: TypeName
    ): FunSpec {
        val isPublic = rule.name in publicRuleNames
        return FunSpec.builder(getFunctionName("parse", rule.name))
            .apply {
                if (!isPublic) {
                    addModifiers(KModifier.PRIVATE)
                }
            }
            .addParameter(ParameterSpec.builder("input", String::class).build())
            .addParameter(
                ParameterSpec.builder("startIndex", Int::class)
                    .apply {
                        if (isPublic) {
                            defaultValue("0")
                        }
                    }
                    .build()
            )
            .apply {
                if (isPublic) {
                    addParameter(
                        ParameterSpec.builder("listener", listenerType.copy(nullable = true))
                            .defaultValue("null")
                            .build()
                    )
                }
            }
            .returns(Int::class)
            .apply {
                if (isPublic) {
                    addStatement(
                        "listener?.%L(input, startIndex)",
                        getFunctionName("enter", rule.name)
                    )
                }
                addStatement("val index = startIndex")
                // https://github.com/square/kotlinpoet/issues/1753
                // addStatement("return %L", generateCode(rule.element, publicRuleNames))
                addCode("return %L", generateParseElementCode(rule.element, publicRuleNames))
                if (isPublic) {
                    beginControlFlow(".also")
                    addStatement(
                        "listener?.%L(input, startIndex, it)",
                        getFunctionName("exit", rule.name)
                    )
                    endControlFlow()
                }
            }
            .build()
    }

    private fun generateParseElementCode(
        element: Element,
        publicRuleNames: Set<String>
    ): CodeBlock = buildCodeBlock {
        when (element) {
            is RuleName -> {
                val functionName = getFunctionName("parse", element.name)
                if (element.name in publicRuleNames) {
                    add("%N(input, index, listener)", functionName)
                } else {
                    add("%N(input, index)", functionName)
                }
            }
            is TerminalValue -> {
                beginControlFlow("run")
                beginControlFlow("if (index >= input.length)")
                addStatement("return@run -1")
                endControlFlow()
                addStatement("val char = input[index]")
                // https://github.com/square/kotlinpoet/issues/1753
                // addStatement("%L", element.value.generateCode("char"))
                add(element.value.generateCode("char"))
                endControlFlow()
            }
            is Repetition -> {
                beginControlFlow("run")
                addStatement("var index = index")
                addStatement("var count = 0")
                val lastCount = element.count.last
                if (lastCount == Int.MAX_VALUE) {
                    beginControlFlow("while (true)")
                } else {
                    beginControlFlow("while (count < %L)", lastCount)
                }
                beginControlFlow(
                    "%L.let",
                    generateParseElementCode(element.element, publicRuleNames)
                )
                beginControlFlow("if (it == -1)")
                addStatement("return@let false")
                endControlFlow()
                addStatement("++count")
                addStatement("index = it")
                addStatement("true")
                endControlFlow("|| break")
                endControlFlow()
                val firstCount = element.count.first
                if (firstCount != 0) {
                    beginControlFlow("if (count < %L)", firstCount)
                    addStatement("return@run -1")
                    endControlFlow()
                }
                addStatement("index")
                endControlFlow()
            }
            is Concatenation -> {
                beginControlFlow("run")
                addStatement("var index = index")
                for (concatenatedElement in element.elements) {
                    beginControlFlow(
                        "%L.let",
                        generateParseElementCode(concatenatedElement, publicRuleNames)
                    )
                    beginControlFlow("if (it == -1)")
                    addStatement("return@run -1")
                    endControlFlow()
                    addStatement("index = it")
                    endControlFlow()
                }
                addStatement("index")
                endControlFlow()
            }
            is Alternation -> {
                beginControlFlow("run")
                for (alternativeElement in element.elements) {
                    beginControlFlow(
                        "%L.let",
                        generateParseElementCode(alternativeElement, publicRuleNames)
                    )
                    beginControlFlow("if (it != -1)")
                    addStatement("return@run it")
                    endControlFlow()
                    endControlFlow()
                }
                addStatement("-1")
                endControlFlow()
            }
        }
    }

    private fun generateListenerInterface(ruleNames: Collection<String>): TypeSpec =
        TypeSpec.interfaceBuilder("Listener")
            .apply {
                ruleNames.forEach {
                    addFunction(
                        FunSpec.builder(getFunctionName("enter", it))
                            .addParameter("input", String::class)
                            .addParameter("startIndex", Int::class)
                            .build()
                    )
                    addFunction(
                        FunSpec.builder(getFunctionName("exit", it))
                            .addParameter("input", String::class)
                            .addParameter("startIndex", Int::class)
                            .addParameter("endIndex", Int::class)
                            .build()
                    )
                }
            }
            .build()

    private fun getFunctionName(prefix: String, ruleName: String): String = buildString {
        append(prefix)
        var uppercase = true
        for (char in ruleName) {
            when (char) {
                in 'A'..'Z' ->
                    if (uppercase) {
                        append(char)
                        uppercase = false
                    } else {
                        append(char - 'A'.code + 'a'.code)
                    }
                in 'a'..'z' ->
                    if (uppercase) {
                        append(char - 'a'.code + 'A'.code)
                        uppercase = false
                    } else {
                        append(char)
                    }
                '-' -> uppercase = true
                else -> {
                    append(char)
                    uppercase = true
                }
            }
        }
    }
}

private fun CodeBlock.Builder.endControlFlow(format: String, vararg args: Any?): CodeBlock.Builder {
    unindent()
    add("} $format\n", *args)
    return this
}
