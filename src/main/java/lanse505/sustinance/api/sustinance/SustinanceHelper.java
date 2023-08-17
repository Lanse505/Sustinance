package lanse505.sustinance.api.sustinance;

import lanse505.sustinance.SustinanceConfig;
import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.drinkable.BaseDrinkable;
import lanse505.sustinance.api.drinkable.Drinkable;
import lanse505.sustinance.api.item.ItemDrink;
import lanse505.sustinance.api.sustinance.hydration.Hydration;
import lanse505.sustinance.api.sustinance.weight.Weight;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Optional;

public class SustinanceHelper {

    // Capability Data-getter Methods
    /**
     * Attempts to obtain the {@linkplain DataSheet} capability object from the {@linkplain Player}.
     * @param player the {@linkplain Player} to obtain thirst data from.
     * @return returns the {@linkplain DataSheet} capability object from the {@linkplain Player}
     */
    public static LazyOptional<DataSheet> getSustinanceData(Player player) {
        return player.getCapability(SustinanceCapabilities.SUSTINANCE_DATA);
    }

    /**
     * Attempts to obtain the {@linkplain Hydration} object from the {@linkplain DataSheet} capability object.
     *
     * @param player the {@linkplain Player} to obtain thirst data from.
     * @return returns the {@linkplain Hydration} capability object from the {@linkplain DataSheet} capability object.
     */
    public static Optional<Hydration> getHydration(Player player) {
        return getSustinanceData(player).map(DataSheet::getHydration).orElse(Optional.empty());
    }

    /**
     * Attempts to obtain the {@linkplain Weight} object from the {@linkplain DataSheet} capability object.
     * @param player the {@linkplain Player} to obtain thirst data from.
     * @return returns the {@linkplain Weight} capability object from the {@linkplain DataSheet} capability object.
     */
    public static Optional<Weight> getWeight(Player player) {
        return getSustinanceData(player).map(DataSheet::getWeight).orElse(Optional.empty());
    }

    // Drinking methods
    /**
     * Attempts to grab the {@linkplain Drinkable} interface object from the {@linkplain ItemStack}.
     * @param drinkable the {@linkplain ItemStack} to attempt to grab the {@linkplain Drinkable} interface from.
     * @return returns the {@linkplain Drinkable} interface object from the {@linkplain ItemStack}.
     */
    public static Drinkable getDrinkableData(ItemStack drinkable) {
        if (isDrinkable(drinkable)) {
            if (drinkable.getItem() instanceof Drinkable) {
                return isDrinkable(drinkable) ? (Drinkable) drinkable.getItem() : BaseDrinkable.NULL;
            }
            return ((ItemDrink) drinkable.getItem()).getDrinkable();
        }
        return BaseDrinkable.NULL;
    }

    /**
     * Checks whether the provided {@linkplain ItemStack} is an instance of the {@linkplain Drinkable} interface.
     * @param stack the {@linkplain ItemStack} to query against.
     * @return returns whether the provided {@linkplain ItemStack} is an instance of the {@linkplain Drinkable} interface.
     */
    public static boolean isDrinkable(ItemStack stack) {
        return isHydrationEnabled() && !stack.isEmpty() && (stack.getItem() instanceof Drinkable || stack.getItem() instanceof ItemDrink);
    }

    /**
     * Checks whether a player is able to drink.
     * <p>
     * Default behaviour checks if the provided {@linkplain ItemStack} is drinkable, as well as checking if either:
     * <ul>
     *     <li>{@code ignoreThirst} is true</li>
     *     <li>If the player's hydration level is less than max</li>
     * </ul>
     * @param consumer the player to check
     * @param ignoreThirst allow drinking regardless of the player's thirst level
     * @return whether the player can drink
     */
    public static boolean canDrink(Player consumer, ItemStack drinkable, boolean ignoreThirst) {
        return isHydrationEnabled() && isDrinkable(drinkable) && (ignoreThirst || SustinanceHelper.getHydration(consumer).get().isThirsty());
    }


    // Config Methods
    /**
     * Checks whether hydration is enabled.
     * @return whether hydration is enabled.
     */
    public static boolean isHydrationEnabled() {
        return SustinanceConfig.getInstance().enableHydration;
    }

    /**
     * Checks whether hydration is enabled.
     * @return whether hydration is enabled.
     */
    public static boolean isNutrientsEnabled() {
        return SustinanceConfig.getInstance().enableNutrition;
    }

    /**
     * Checks whether hydration is enabled.
     * @return whether hydration is enabled.
     */
    public static boolean isWeightEnabled() {
        return SustinanceConfig.getInstance().enableWeight;
    }
}
