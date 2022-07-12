package dev.rodrick.acer

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

class InitializerProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
    private val options: Map<String, String>
) : SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver
            .getSymbolsWithAnnotation("dev.rodrick.acer.annotations.Init")
            .filterIsInstance<KSFunctionDeclaration>()

        if (!symbols.iterator().hasNext()) return emptyList()

        val file = FileSpec.builder("dev.rodrick.acer", "Initializer")
            .addType(
                TypeSpec.objectBuilder("Initializer")
                    .addFunction(
                        FunSpec.builder("init").apply {
                            symbols.forEach { symbol ->
                                addStatement(
                                    "%M()",
                                    MemberName(
                                        "${symbol.packageName.asString()}.${symbol.parentDeclaration?.simpleName?.asString()}",
                                        symbol.simpleName.asString()
                                    )
                                )
                            }
                        }
                            .build()
                    )
                    .build()
            )
            .build()

        file.writeTo(codeGenerator, Dependencies(false, *resolver.getAllFiles().toList().toTypedArray()))

        return symbols.filterNot { it.validate() }.toList()
    }
}