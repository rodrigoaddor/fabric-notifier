package dev.rodrick.acer.config

import kotlinx.serialization.Serializable

object AcerConfig: ConfigHandler() {
    @Serializable()
    data class Data(
        var apiKey: String = "API_KEY",
        var devices: Set<String> = emptySet()
    )
}