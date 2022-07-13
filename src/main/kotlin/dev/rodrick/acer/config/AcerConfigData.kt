package dev.rodrick.acer.config

import kotlinx.serialization.Serializable

@Serializable
data class AcerConfigData(
    val replantSaplings: ReplantSaplings = ReplantSaplings(),
    val notifier: Notifier = Notifier(),
    val finder: Finder = Finder()
) {
    @Serializable
    data class ReplantSaplings(
        val enabled: Boolean = false,
        val chance: Double = 1.0,
    )

    @Serializable
    data class Notifier(
        val onJoin: Boolean = false,
        val onLeave: Boolean = false,
        val apiKey: String = "",
        val devices: Set<String> = emptySet()
    )

    @Serializable
    data class Finder(
        val range: Int = 10,
        val duration: Float = 10f
    )
}
