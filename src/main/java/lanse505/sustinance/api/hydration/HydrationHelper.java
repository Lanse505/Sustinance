package lanse505.sustinance.api.hydration;

import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.drinkable.BaseDrinkable;
import lanse505.sustinance.api.drinkable.Drinkable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

public class HydrationHelper {

    /**
     * Attempts to obtain the {@linkplain LazyOptional} capability object from the {@linkplain Player}.
     * @param player the {@linkplain Player} to obtain thirst data from.
     * @return returns the {@linkplain LazyOptional} capability object from the {@linkplain Player}
     */
    public static Hydration getHydrationData(Player player) {
        return player.getCapability(SustinanceCapabilities.HYDRATION).orElseThrow(() -> new IllegalArgumentException("Hydration Cap was Null!"));
    }

    /**
     * Attempts to grab the {@linkplain Drinkable} interface object from the {@linkplain ItemStack}.
     * @param drinkable the {@linkplain ItemStack} to attempt to grab the {@linkplain Drinkable} interface from.
     * @return returns the {@linkplain Drinkable} interface object from the {@linkplain ItemStack}.
     */
    public static Drinkable getDrinkableData(ItemStack drinkable) {
        return isDrinkable(drinkable) ? (Drinkable) drinkable.getItem() : BaseDrinkable.NULL;
    }

    /**
     * Checks whether the provided {@linkplain ItemStack} is an instance of the {@linkplain Drinkable} interface.
     * @param stack the {@linkplain ItemStack} to query against.
     * @return returns whether the provided {@linkplain ItemStack} is an instance of the {@linkplain Drinkable} interface.
     */
    public static boolean isDrinkable(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() instanceof Drinkable;
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
        return isDrinkable(drinkable) && (ignoreThirst || getHydrationData(consumer).isThirsty());
    }

    /**
     * Checks whether thirst is enabled.
     * @return whether thirst is enabled.
     */
    public static boolean isThirstEnabled() {
        return true; // TODO: Implement Config-check
    }
}
