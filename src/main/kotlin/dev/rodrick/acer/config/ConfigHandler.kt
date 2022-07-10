package dev.rodrick.acer.config

import com.charleskorn.kaml.EmptyYamlDocumentException
import com.charleskorn.kaml.Yaml
import dev.rodrick.acer.AcerMod
import net.fabricmc.loader.api.FabricLoader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

open class ConfigHandler {
    var data = AcerConfig.Data()

    private val file = FabricLoader.getInstance().configDir.resolve("${AcerMod.MOD_ID}.yml").toFile()

    fun load() = try {
        FileInputStream(file).use {
            data = Yaml.default.decodeFromStream(AcerConfig.Data.serializer(), it)
        }
    } catch (e: Exception) {
        when (e) {
            is FileNotFoundException, is EmptyYamlDocumentException -> {
                data = AcerConfig.Data()
            }
            else -> throw e
        }
    } finally {
        save()
    }

    private fun save() = FileOutputStream(file).use {
        Yaml.default.encodeToStream(AcerConfig.Data.serializer(), data, it)
    }
}