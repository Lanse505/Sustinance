package lanse505.sustinance.api.drinkable;

import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

public interface Drinkable {

    int getThirstModifier();
    int getThirstModifier(@Nullable LivingEntity consumer);

    float getHydrationModifier();
    float getHydrationModifier(@Nullable LivingEntity consumer);

    void onConsume();

}
