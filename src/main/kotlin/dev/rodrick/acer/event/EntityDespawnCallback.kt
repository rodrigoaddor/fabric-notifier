package dev.rodrick.acer.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.entity.Entity

fun interface EntityDespawnCallback {
    fun onDespawn(entity: Entity)

    companion object {
        val EVENT: Event<EntityDespawnCallback> =
            EventFactory.createArrayBacked(EntityDespawnCallback::class.java) { listeners ->
                EntityDespawnCallback { entity ->
                    listeners.forEach {
                        it.onDespawn(entity)
                    }
                }
            }
    }
}