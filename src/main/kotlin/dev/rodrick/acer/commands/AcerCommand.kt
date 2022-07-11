package dev.rodrick.acer.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import dev.rodrick.acer.AcerMod
import dev.rodrick.acer.config.AcerConfig
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

object AcerCommand : BaseCommand {
    private val reloadConfig = literal("reload").executes { context ->
        try {
            AcerConfig.load()
            context.source.sendFeedback(Text.literal("[Acer] Config reloaded"), true)
            0
        } catch (e: Exception) {
            AcerMod.logger.warn("Error reloading config: $e")
            context.source.sendFeedback(
                Text.literal("[Acer] Error reloading config, check console for more information"), false
            )
            1
        }
    }

    private val listConfig = literal("config").executes { context ->
        context.source.sendFeedback(
            Text.literal(AcerConfig.data.toString()),
            false
        )
        0
    }

    override val command: LiteralArgumentBuilder<ServerCommandSource> = literal("acer")
        .then(reloadConfig)
        .then(listConfig)
}