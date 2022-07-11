package dev.rodrick.acer.config

import com.charleskorn.kaml.EmptyYamlDocumentException
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import dev.rodrick.acer.AcerMod
import net.fabricmc.loader.api.FabricLoader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream

object AcerConfig {
    var data = AcerConfigData()

    private val file = FabricLoader.getInstance().configDir.resolve("${AcerMod.MOD_ID}.yml").toFile()
    private val yaml = Yaml(configuration = YamlConfiguration(strictMode = false))

    fun load() = try {
        FileInputStream(file).use {
            data = yaml.decodeFromStream(AcerConfigData.serializer(), it)
        }
    } catch (e: Exception) {
        when (e) {
            is FileNotFoundException, is EmptyYamlDocumentException -> {
                data = AcerConfigData()
            }
            else -> throw e
        }
    } finally {
        save()
    }

    private fun save() = FileOutputStream(file).use {
        yaml.encodeToStream(AcerConfigData.serializer(), data, it)
    }
}