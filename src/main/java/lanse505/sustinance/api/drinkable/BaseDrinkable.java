package lanse505.sustinance.api.drinkable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class BaseDrinkable implements Drinkable {

    public static BaseDrinkable NULL = new BaseDrinkable(0, 0.0F);

    private final int thirstModifier;
    private final float hydrationModifier;

    public BaseDrinkable(int thirstModifier, float hydrationModifier) {
        this.thirstModifier = thirstModifier;
        this.hydrationModifier = hydrationModifier;
    }

    @Override
    public int getThirstModifier() {
        return this.thirstModifier;
    }

    @Override
    public int getThirstModifier(@Nullable LivingEntity consumer) {
        return getThirstModifier();
    }

    @Override
    public float getHydrationModifier() {
        return this.hydrationModifier;
    }

    @Override
    public float getHydrationModifier(@Nullable LivingEntity consumer) {
        return getHydrationModifier();
    }

    @Override
    public void onConsume(ItemStack consumed, Level level, Player consumer) {}
}
