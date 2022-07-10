package dev.rodrick.acer.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.rodrick.acer.AcerConfig
import dev.rodrick.acer.AcerMod
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object AcerCommand : BaseCommand {
    private val reloadConfig = literal("reload")
        .executes { context ->
            try {
                AcerConfig.load()
                context.source.sendFeedback(Text.literal("Config reloaded"), true)
                0
            } catch (e: Exception) {
                AcerMod.logger.warn("Error reloading config: $e")
                1
            }
        }

    private val listConfig = literal("config")
        .executes { context ->
            val config = AcerConfig.config!!
            context.source.sendFeedback(Text.literal("apiKey: ${config.apiKey}   devices: ${config.deviceNames}"), false)
            0
        }

    override val command: LiteralArgumentBuilder<ServerCommandSource> = literal("acer")
        .then(reloadConfig)
        .then(listConfig)
}