package lanse505.sustinance.api.drinkable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public interface Drinkable {

    int getThirstModifier();
    int getThirstModifier(@Nullable LivingEntity consumer);

    float getHydrationModifier();
    float getHydrationModifier(@Nullable LivingEntity consumer);

    void onConsume(ItemStack consumed, Level level, Player consumer);

}
