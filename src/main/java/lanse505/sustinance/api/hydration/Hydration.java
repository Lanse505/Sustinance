package lanse505.sustinance.api.hydration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;

public interface Hydration extends INBTSerializable<CompoundTag> {

    /**
     * {@linkplain LivingEntity}-agnostic version of {@link #drink(Item, ItemStack, LivingEntity)}.
     * <p>
     * Used for handling "drinking" without a {@linkplain LivingEntity}-context.
     *
     * @param thirstLevelModifier    the modifier for the thirst level.
     * @param hydrationLevelModifier the modifier for the hydration level.
     */
    void drink(int thirstLevelModifier, float hydrationLevelModifier);

    /**
     * {@linkplain LivingEntity}-dependant version of {@link #drink(int, float)}.
     * <p>
     * Used for handling "drinking" with a {@linkplain LivingEntity}-context.
     * @param drinkable
     * @param stack
     * @param consumer
     */
    void drink(Item drinkable, ItemStack stack, @Nullable LivingEntity consumer);

    /**
     * {@return Returns the current thirst-level}
     */
    int getThirst();

    /**
     * {@return Returns the last thirst-level}
     */
    int getLastThirst();

    /**
     * Default spec:
     * <pre>{@code
     * public boolean isThirsty() {
     *   return this.lastThirstLevel() <= ImplHydrationData.MAX_THIRST;
     * }
     * }</pre>
     * {@return Returns whether the capability-holding entity is thirsty}
     */
    boolean isThirsty();

    /**
     * Adds to the hydration value.
     *
     * @param hydration the hydration-value to add.
     * @return Returns the changed hydration value.
     */
    float addHydration(float hydration);

    /**
     * Adds to the thirst value.
     *
     * @param thirst the thirst-value to add.
     * @return Returns the changed thirst value.
     */
    int addThirst(int thirst);

    /**
     * {@return Returns the current hydration-level}
     */
    float getHydrationLevel();

    /**
     * @param thirst the new thirst-level to replace the old one.
     */
    void setThirst(int thirst);

    /**
     * @param hydration the new hydration-level to replace the old one.
     */
    void setHydration(float hydration);

}
