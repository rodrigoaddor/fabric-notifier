package dev.rodrick.acer.effect

import dev.rodrick.acer.annotations.Init
import dev.rodrick.acer.config.AcerConfig
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.entity.SignBlockEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand

object SignEdit {
    @Init
    fun init() {
        UseBlockCallback.EVENT.register { player, world, hand, hitResult ->
            val blockEntity = world.getBlockEntity(hitResult.blockPos)
            val enabled = AcerConfig.data.editableSigns

            if (enabled && player.isSneaking && !world.isClient && blockEntity is SignBlockEntity && hand == Hand.MAIN_HAND && player.handItems.all { it.isEmpty }) {
                player.openEditSignScreen(blockEntity)
                ActionResult.SUCCESS
            }

            ActionResult.PASS
        }
    }
}