package dev.rodrick.acer.config

import kotlinx.serialization.Serializable

@Serializable()
data class AcerConfigData(
    val replantSaplings: ReplantSaplings = ReplantSaplings()
) {
    @Serializable
    data class ReplantSaplings(
        val enabled: Boolean = false,
        val chance: Double = 1.0,
    )
}
