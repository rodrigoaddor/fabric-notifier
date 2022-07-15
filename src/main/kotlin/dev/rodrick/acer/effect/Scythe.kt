package dev.rodrick.acer.effect

import dev.rodrick.acer.AcerMod
import dev.rodrick.acer.annotations.Init
import net.fabricmc.fabric.api.event.player.UseBlockCallback
import net.minecraft.block.CropBlock
import net.minecraft.entity.ItemEntity
import net.minecraft.item.BlockItem
import net.minecraft.loot.context.LootContext
import net.minecraft.loot.context.LootContextParameters
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.tag.BlockTags
import net.minecraft.tag.TagKey
import net.minecraft.util.ActionResult
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d
import net.minecraft.util.registry.Registry

object Scythe {
    private val CROPS = BlockTags.CROPS
    private val SCYTHES = TagKey.of(Registry.ITEM_KEY, Identifier(AcerMod.MOD_ID, "scythes"))

    @Init
    fun init() {
        UseBlockCallback.EVENT.register { player, world, hand, hitResult ->
            val blockPos = hitResult.blockPos
            val blockState = world.getBlockState(blockPos)
            val heldStack = player.getStackInHand(hand)
            val blockEntity = world.getBlockEntity(blockPos)
            val serverWorld = world as? ServerWorld

            if (player.itemCooldownManager.isCoolingDown(heldStack.item)) {
                return@register ActionResult.FAIL
            }

            if (blockState.isIn(CROPS) && heldStack.isIn(SCYTHES) && (blockState.block as? CropBlock)?.isMature(
                    blockState
                ) == true
            ) {
                if (serverWorld != null) {
                    val lootContext = LootContext.Builder(serverWorld).random(serverWorld.random)
                        .parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
                        .parameter(LootContextParameters.TOOL, heldStack)
                        .optionalParameter(LootContextParameters.THIS_ENTITY, player)
                        .optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity)

                    blockState.getDroppedStacks(lootContext).forEach { stack ->
                        if ((stack.item as? BlockItem)?.let {
                                @Suppress("DEPRECATION")
                                it.block.registryEntry.isIn(CROPS)
                            } == true) {
                            stack.count--
                        }

                        val entity =
                            ItemEntity(serverWorld, blockPos.x + .5, blockPos.y + .0, blockPos.z + 0.5, stack).apply {
                                setPickupDelay(10)
                            }

                        serverWorld.spawnEntity(entity)
                    }

                    heldStack.damage(1, player) {
                        serverWorld.playSound(
                            null, it.x, it.y, it.z, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 1.0f, 1.0f
                        )
                    }

                    player.itemCooldownManager.set(heldStack.item, 10)

                    serverWorld.playSound(
                        null,
                        blockPos.x.toDouble(),
                        blockPos.y.toDouble(),
                        blockPos.z.toDouble(),
                        blockState.soundGroup.breakSound,
                        SoundCategory.BLOCKS,
                        0.9f,
                        1.0f,
                    )

                    serverWorld.setBlockState(blockPos, blockState.block.defaultState)
                }

                ActionResult.SUCCESS
            }

            ActionResult.PASS
        }
    }
}