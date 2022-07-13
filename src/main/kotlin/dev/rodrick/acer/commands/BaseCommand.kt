package dev.rodrick.acer.commands

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource

interface BaseCommand {
    fun register(
        dispatcher: CommandDispatcher<ServerCommandSource>,
        registryAccess: CommandRegistryAccess,
        environment: CommandManager.RegistrationEnvironment
    )
}