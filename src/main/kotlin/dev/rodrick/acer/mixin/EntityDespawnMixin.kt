package dev.rodrick.acer.mixin

import dev.rodrick.acer.event.EntityDespawnCallback
import net.minecraft.entity.Entity
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(Entity::class)
class EntityDespawnMixin {
    @Inject(at = [At("TAIL")], method = ["remove"])
    private fun onRemove(info: CallbackInfo) {
        @Suppress("CAST_NEVER_SUCCEEDS")
        EntityDespawnCallback.EVENT.invoker().onDespawn(this as Entity)
    }
}