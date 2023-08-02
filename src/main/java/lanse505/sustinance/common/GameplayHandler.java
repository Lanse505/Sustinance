package lanse505.sustinance.common;

import lanse505.sustinance.common.thirst.hydration.HydrationHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class GameplayHandler {

    @SubscribeEvent
    public static void handleDrink(LivingEntityUseItemEvent.Finish event) {
        ItemStack consumable = event.getItem();
        if (HydrationHelper.isDrinkable(consumable) && event.getEntity() instanceof Player player) {
            HydrationHelper.getHydrationData(player).drink(consumable.getItem(), consumable, player);
        }
    }
}
