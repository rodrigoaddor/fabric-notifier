package dev.rodrick.acer.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.server.command.ServerCommandSource

interface BaseCommand {
    val command: LiteralArgumentBuilder<ServerCommandSource>
}