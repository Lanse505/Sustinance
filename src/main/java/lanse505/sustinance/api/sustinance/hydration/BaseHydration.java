package lanse505.sustinance.api.sustinance.hydration;

import lanse505.sustinance.Sustinance;
import lanse505.sustinance.api.drinkable.Drinkable;
import lanse505.sustinance.api.sustinance.SustinanceHelper;
import lanse505.sustinance.common.network.packets.ClientboundUpdateHydrationDataPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BaseHydration implements Hydration {

    public static final int MAX_THIRST = 20;
    public static final int MIN_THIRST = 0;

    public static final float MAX_HYDRATION = 40.0f;
    public static final float MIN_HYDRATION = 0.0f;

    private int thirst;
    private int lastThirst;
    private float hydrationLevel;


    public BaseHydration() {
        this.thirst = MAX_THIRST;
        this.hydrationLevel = 5.0f;
    }

    @Override
    public void drink(int thirstModifier, float hydrationLevelModifier) {
        this.lastThirst = thirst;
        this.thirst = Math.min(this.thirst + thirstModifier, MAX_THIRST);
        this.hydrationLevel = Math.min(this.hydrationLevel + thirstModifier * hydrationLevelModifier * 2.0F, MAX_HYDRATION);
    }

    @Override
    public void drink(Item consumable, ItemStack stack, @Nullable LivingEntity consumer) {
        if (consumable instanceof Drinkable) {
            Drinkable drinkable = SustinanceHelper.getDrinkableData(stack);
            this.drink(drinkable.getThirstModifier(stack), drinkable.getHydrationModifier(stack));
            if (consumer instanceof ServerPlayer player) {
                Sustinance.handler.sendTo(new ClientboundUpdateHydrationDataPacket(thirst, lastThirst, hydrationLevel), player);
            }
        }
    }


    @Override
    public int getThirst() {
        return this.thirst;
    }

    @Override
    public int getLastThirst() {
        return this.lastThirst;
    }

    @Override
    public boolean isThirsty() {
        return this.thirst <= MAX_THIRST;
    }

    @Override
    public float addHydration(float hydration) {
        return this.hydrationLevel = Math.min(Math.max(this.hydrationLevel + hydration, MIN_HYDRATION), MAX_HYDRATION);
    }

    @Override
    public int addThirst(int thirst) {
        setLastThirst(this.thirst);
        return this.thirst = Math.min(Math.max(this.thirst + thirst, MIN_THIRST), MAX_THIRST);
    }

    @Override
    public float getHydrationLevel() {
        return this.hydrationLevel;
    }

    @Override
    public void setThirst(int thirst) {
        this.thirst = Math.min(Math.max(thirst, MIN_THIRST), MAX_THIRST);
    }

    @Override
    public void setLastThirst(int lastThirst) {
        this.lastThirst = Math.min(Math.max(lastThirst, MIN_THIRST), MAX_THIRST);;
    }

    @Override
    public void setHydration(float hydration) {
        this.hydrationLevel = Math.min(Math.max(hydration, MIN_HYDRATION), MAX_HYDRATION);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag hydrationData = new CompoundTag();
        hydrationData.putInt("thirst", this.thirst);
        hydrationData.putInt("lastThirst", this.lastThirst);
        hydrationData.putFloat("hydrationLevel", this.hydrationLevel);
        return hydrationData;
    }

    @Override
    public void deserializeNBT(CompoundTag hydrationData) {
        this.thirst = hydrationData.getInt("thirst");
        this.lastThirst = hydrationData.getInt("lastThirst");
        this.hydrationLevel = hydrationData.getFloat("hydrationLevel");
    }
}
