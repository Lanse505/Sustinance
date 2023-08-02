package lanse505.sustinance.common.handlers;

import lanse505.sustinance.api.hydration.HydrationHelper;
import lanse505.sustinance.api.item.ItemDrink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GameplayHandler {

    /**
     * Handles the actual addition of hydration data to the player.
     * @param event the event we're listening after.
     */
    @SubscribeEvent
    public static void handleDrink(LivingEntityUseItemEvent.Finish event) {
        ItemStack consumable = event.getItem();
        if (HydrationHelper.isDrinkable(consumable) && event.getEntity() instanceof Player player) {
            HydrationHelper.getHydrationData(player).drink(consumable.getItem(), consumable, player);
        }
    }

    /**
     * Handles redirecting to the Player-sensitive version of {@linkplain Item#getUseDuration(ItemStack)}
     * @param event the event we're listening after.
     */
    @SubscribeEvent
    public static void getSpecialUseDuration(LivingEntityUseItemEvent.Start event) {
        ItemStack stack = event.getItem();
        if (event.getEntity() instanceof Player player && stack.getItem() instanceof ItemDrink drink) {
            event.setDuration(drink.getUseDuration(player, stack));
        }
    }
}
