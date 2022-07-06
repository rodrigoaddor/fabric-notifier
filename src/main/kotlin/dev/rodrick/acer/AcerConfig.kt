package dev.rodrick.acer

import net.fabricmc.loader.api.FabricLoader
import org.spongepowered.configurate.CommentedConfigurationNode
import org.spongepowered.configurate.ConfigurateException
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.ObjectMapper
import org.spongepowered.configurate.objectmapping.meta.NodeResolver
import org.spongepowered.configurate.yaml.NodeStyle
import org.spongepowered.configurate.yaml.YamlConfigurationLoader

@ConfigSerializable
class AcerConfig {
    var apiKey: String? = "API_KEY"
        set(value) {
            field = value
            save(this)
        }

    var deviceNames: Set<String>? = emptySet()
        set(value) {
            field = value
            save(this)
        }

    companion object {
        private val file = FabricLoader.getInstance().configDir.resolve("${AcerMod.MOD_ID}.yml")

        private val loader = YamlConfigurationLoader.builder()
            .path(file)
            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
            .defaultOptions { options ->
                options.serializers { builder ->
                    builder.registerAnnotatedObjects(
                        ObjectMapper.factoryBuilder()
                            .addNodeResolver(NodeResolver.nodeKey())
                            .build()
                    )
                }
            }.build()

        private lateinit var node: CommentedConfigurationNode

        fun load() = try {
            node = loader.load()
            node.get<AcerConfig>()?.let(::save)
        } catch (e: ConfigurateException) {
            AcerMod.logger.error("Error loading config file: $e")
        }

        fun save(config: AcerConfig) = try {
            node.set(AcerConfig::class.java, config)
            loader.save(node)
        } catch (e: ConfigurateException) {
            AcerMod.logger.error("Error saving config file: $e")
        }

        val config: AcerConfig?
            get() = node.get<AcerConfig>()
    }
}
