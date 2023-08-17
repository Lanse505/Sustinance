package lanse505.sustinance.common;

import lanse505.sustinance.Sustinance;
import lanse505.sustinance.SustinanceConfig;
import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.item.ItemDrink;
import lanse505.sustinance.api.sustinance.SustinanceHelper;
import lanse505.sustinance.api.sustinance.hydration.BaseHydration;
import lanse505.sustinance.api.sustinance.hydration.Hydration;
import lanse505.sustinance.api.sustinance.weight.Weight;
import lanse505.sustinance.client.RenderHelper;
import lanse505.sustinance.common.network.SustinanceNetworkHandler;
import lanse505.sustinance.common.network.packets.ClientboundUpdateHydrationDataPacket;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber
public class GameplayHandler {

    /**
     * Handles the actual addition of hydration data to the player.
     * @param event the event we're listening after.
     */
    @SubscribeEvent
    public static void handleDrink(LivingEntityUseItemEvent.Finish event) {
        ItemStack consumable = event.getItem();
        if (SustinanceHelper.isDrinkable(consumable) && event.getEntity() instanceof Player player) {
            Optional<Hydration> optional = SustinanceHelper.getHydration(player);
            optional.ifPresent(hydration -> hydration.drink(consumable.getItem(), consumable, player));
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

    private static final boolean debug = true;
    @SubscribeEvent
    public static void handleDebug(TickEvent.PlayerTickEvent event) {
        if (debug && event.player.level().getGameTime() % 60 == 0 && event.phase == TickEvent.Phase.END && !event.player.level().isClientSide() && event.player.isAlive()) {
            Sustinance.LOGGER.info(event.player.getCapability(SustinanceCapabilities.SUSTINANCE_DATA).orElseThrow(IllegalArgumentException::new).toString());
        }
    }

    @SubscribeEvent
    public static void handleThirstTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            handleThirst(event.player);
        }
    }

    @SubscribeEvent
    public static void handleWeightUpdateTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            handleWeightUpdate(event.player);
        }
    }

    private static void handleWeightUpdate(Player player) {
        if ((player.level().getGameTime() + player.getId()) % SustinanceConfig.getInstance().weightConfig.getDefaultUpdateDelayInTicks() == 0) {
            Optional<Weight> weight = SustinanceHelper.getWeight(player);
            if (weight.isPresent()) {
                // TODO: Rework this once we get a Item -> Weight system implemented
                int totalWeight = 0;
                Inventory playerInventory = player.getInventory();
                for (int i = 0; i < playerInventory.items.size(); i++) {
                    totalWeight += playerInventory.items.get(i).getCount();
                }
                for (int i = 0; i < playerInventory.armor.size(); i++) {
                    totalWeight += playerInventory.armor.get(i).getCount();
                }
                totalWeight += playerInventory.offhand.get(0).getCount();
                weight.get().setWeight(totalWeight);
                // TODO: Send Packet to update Weight Data
            }
        }
    }

    /**
     * This method is very much inspired by parts of {@linkplain net.minecraft.world.food.FoodData#tick(Player)}
     * @param player the Player to handle the thirst of.
     */
    private static void handleThirst(Player player) {
        Optional<Hydration> optional = SustinanceHelper.getHydration(player);
        if (optional.isPresent()) {
            Hydration hydration = optional.get();
            Difficulty difficulty = player.level().getDifficulty();
            float exhaustion = player.getFoodData().getExhaustionLevel();
            if (exhaustion > 4.0F) {
                if (hydration.getHydrationLevel() > BaseHydration.MIN_HYDRATION) {
                    hydration.addHydration(-1.0f);
                    Sustinance.handler.sendTo(
                            new ClientboundUpdateHydrationDataPacket(
                                    hydration.getThirst(), hydration.getLastThirst(), hydration.getHydrationLevel()),
                            (ServerPlayer) player
                    );
                } else if (difficulty != Difficulty.PEACEFUL) {
                    hydration.addThirst(-1);
                    Sustinance.handler.sendTo(
                            new ClientboundUpdateHydrationDataPacket(
                                    hydration.getThirst(), hydration.getLastThirst(), hydration.getHydrationLevel()),
                            (ServerPlayer) player
                    );
                }
            }
            if (hydration.getThirst() <= BaseHydration.MIN_THIRST) {
                if ((hydration.getThirst() > 10.0F || difficulty == Difficulty.HARD || hydration.getThirst() > 1.0F && difficulty == Difficulty.NORMAL)) {
                    player.hurt(player.damageSources().starve(), 1.0F);
                }
            }
        }
    }
}
