package dev.rodrick.acer.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MobEntity.class)
public interface EntityAccessor {
    @Accessor("lootTable")
    void setLootTable(Identifier identifier);
}
