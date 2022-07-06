package dev.rodrick.acer

import net.fabricmc.api.ModInitializer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Suppress("UNUSED")
object AcerMod : ModInitializer {
    const val MOD_NAME = "Acer"
    const val MOD_ID = "acer"

    val logger: Logger = LogManager.getLogger(MOD_NAME)

    override fun onInitialize() {
        AcerConfig.load()
    }
}