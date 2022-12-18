package dev.rodrick.acer.hooks

import dev.rodrick.acer.annotations.Init
import kotlinx.serialization.Serializable
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents
import net.minecraft.server.network.ServerPlayNetworkHandler

object PlayerHooks: Webhooks() {
    @Init
    fun init() {
        ServerPlayConnectionEvents.JOIN.register { handler, _, _ ->
            config.onJoin?.run {
                handlePlayerWithBlacklist(handler, this)
            }
        }

        ServerPlayConnectionEvents.DISCONNECT.register { handler, _ ->
            config.onLeave?.run {
                handlePlayerWithBlacklist(handler, this)
            }
        }
    }

    private fun handlePlayerWithBlacklist(handler: ServerPlayNetworkHandler, options: Options?) {
        val playerName = handler.player.name.string
        options?.run {
            if (blacklist?.contains(playerName) != true) {
                send(replacePlaceholders(message, mapOf("player" to playerName)))
            }
        }
    }

    @Serializable
    data class Options(
        val blacklist: Set<String>? = emptySet(),
        val message: String = "",
    )
}