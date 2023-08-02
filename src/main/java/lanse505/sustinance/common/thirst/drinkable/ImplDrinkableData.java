package lanse505.sustinance.common.thirst.drinkable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class ImplDrinkableData implements DrinkableData {

    private int thirstModifier;
    private float hydrationModifier;

    public ImplDrinkableData(int thirstModifier, float hydrationModifier) {
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
    public void onConsume() {}

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag drinkableData = new CompoundTag();
        drinkableData.putInt("thirstModifier", this.thirstModifier);
        drinkableData.putFloat("hydrationModifier", this.hydrationModifier);
        return drinkableData;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.thirstModifier = nbt.getInt("thirstModifier");
        this.hydrationModifier = nbt.getFloat("hydrationModifier");
    }
}
