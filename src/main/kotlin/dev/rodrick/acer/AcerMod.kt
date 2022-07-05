package dev.rodrick.acer

import net.fabricmc.api.ModInitializer

@Suppress("UNUSED")
object AcerMod : ModInitializer {
    const val MOD_NAME = "Acer"
    const val MOD_ID = "acer"

    override fun onInitialize() {
        AcerConfig.init()
    }
}