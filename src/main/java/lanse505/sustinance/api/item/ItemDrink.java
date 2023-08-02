package lanse505.sustinance.api.item;

import lanse505.sustinance.api.drinkable.BaseDrinkable;
import lanse505.sustinance.api.drinkable.Drinkable;
import lanse505.sustinance.api.hydration.Hydration;
import lanse505.sustinance.api.hydration.HydrationHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public abstract class ItemDrink extends Item {

    @Nonnull
    private final Drinkable drinkable;

    public ItemDrink(Properties properties) {
        super(properties);
        this.drinkable = BaseDrinkable.NULL;
    }

    public ItemDrink(Properties properties, @Nonnull Drinkable drinkable) {
        super(properties);
        this.drinkable = drinkable;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        Hydration hydration = HydrationHelper.getHydrationData(player);
        if (hydration.isThirsty()) return new InteractionResultHolder<>(InteractionResult.SUCCESS, player.getItemInHand(usedHand));
        return super.use(level, player, usedHand);
    }

    /**
     * Used to actually consume and add the drinkable stats to the player.
     * Override {@linkplain #getCraftingRemainingItem(ItemStack)} to handle what gets returned when consuming the item.
     * Override this method to handle custom drinking outcomes.
     *
     * @param stack the {@linkplain ItemStack} being consumed.
     * @param level the {@linkplain Level} where the drink is being consumed.
     * @param livingEntity the {@linkplain LivingEntity} consuming the drink.
     * @return returns the {@linkplain ItemStack} after consumption.
     */
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof Player player) {
           Hydration hydration = HydrationHelper.getHydrationData(player);
           hydration.drink(this, stack, player);
           onConsume(drinkable, stack, level, player);
           return this.getCraftingRemainingItem(stack);
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }


    /**
     * Used to trigger additional effects when the drink is consumed.
     * <p>
     * You can either override this on your item or build in the behaviour as part of the onConsume method of your {@linkplain Drinkable} implementation.
     * @param drinkable the {@linkplain Drinkable} reference object.
     * @param stack the {@linkplain ItemStack} being used.
     * @param level the {@linkplain Level} that the item is being consumed in.
     * @param player the {@linkplain Player} consuming the item.
     */
    public void onConsume(Drinkable drinkable, ItemStack stack, Level level, Player player) {
        drinkable.onConsume(stack, level, player);
    }

    /**
     * Override this method to change the Use-Duration of your drink item.
     * @param stack the {@linkplain ItemStack} to use for reference for the Use-duration.
     * @return returns the Use-duration in ticks.
     */
    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    /**
     * Player-sensitive version of {@linkplain #getUseDuration(ItemStack)}
     * Defaults to calling: {@linkplain #getUseDuration(ItemStack)}
     * @param player the {@linkplain Player} that's using the item.
     * @param stack the {@linkplain ItemStack} to use for reference for the Use-duration
     * @return returns the Use-duration in ticks.
     */
    public int getUseDuration(Player player, ItemStack stack) {
        return getUseDuration(stack);
    }

    /**
     * Overridden to use the Drink animation by default.
     * @param stack the {@linkplain ItemStack} instance to check the animation against.
     * @return returns the {@linkplain UseAnim#DRINK} animation.
     */
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    /**
     * Used to get the {@linkplain Drinkable} reference object.
     * @return returns the {@linkplain Drinkable} reference object.
     */
    @Nonnull
    public Drinkable getDrinkable() {
        return drinkable;
    }
}
