package lanse505.sustinance.common.thirst.drinkable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public interface DrinkableData extends INBTSerializable<CompoundTag> {

    int getThirstModifier();
    int getThirstModifier(@Nullable LivingEntity consumer);

    float getHydrationModifier();
    float getHydrationModifier(@Nullable LivingEntity consumer);

    void onConsume();

}
