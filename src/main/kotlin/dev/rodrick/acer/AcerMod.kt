package dev.rodrick.acer

import dev.rodrick.acer.commands.AcerCommand
import dev.rodrick.acer.commands.BaseCommand
import dev.rodrick.acer.config.AcerConfig
import dev.rodrick.acer.events.SaplingPlanter
import net.fabricmc.api.DedicatedServerModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object AcerMod : DedicatedServerModInitializer {
    const val MOD_NAME = "Acer"
    const val MOD_ID = "acer"

    val logger: Logger = LogManager.getLogger(MOD_NAME)

    private val COMMANDS = listOf<BaseCommand>(
        AcerCommand
    )

    override fun onInitializeServer() {
        AcerConfig.load()

        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            COMMANDS.forEach { dispatcher.register(it.command) }
        }

        SaplingPlanter.init()
    }
}