package lanse505.sustinance.common.handlers;

import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.hydration.BaseHydration;
import lanse505.sustinance.api.hydration.Hydration;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class HydrationTickHandler {

    @SubscribeEvent
    public static void handleThirstTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            handleThirst(event.player);
        }
    }

    public static void handleThirst(Player player) {
        Hydration thirst = player.getCapability(SustinanceCapabilities.HYDRATION).orElseThrow(() -> new IllegalArgumentException("Hydration Cap was Null!"));
        if (thirst instanceof BaseHydration data) {
            Difficulty difficulty = player.level().getDifficulty();
            float exhaustion = player.getFoodData().getExhaustionLevel();
            if (exhaustion > 4.0F) {
                if (data.getHydrationLevel() > BaseHydration.MIN_HYDRATION) {
                    data.addHydration(-1.0f);
                } else if (difficulty != Difficulty.PEACEFUL) {
                    data.addThirst(-1);
                }
            }
            if (data.getThirst() <= BaseHydration.MIN_THIRST) {
                if ((data.getThirst() > 10.0F || difficulty == Difficulty.HARD || data.getThirst() > 1.0F && difficulty == Difficulty.NORMAL)) {
                    player.hurt(player.damageSources().starve(), 1.0F);
                }
            }
        }
    }
}
