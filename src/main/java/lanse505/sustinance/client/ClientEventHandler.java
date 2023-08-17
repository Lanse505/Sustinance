package lanse505.sustinance.client;

import lanse505.sustinance.Sustinance;
import lanse505.sustinance.api.sustinance.SustinanceHelper;
import lanse505.sustinance.api.sustinance.hydration.BaseHydration;
import lanse505.sustinance.api.sustinance.hydration.Hydration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {

    private static final Minecraft client = Minecraft.getInstance();

    private static final ResourceLocation DRIPLET_RESOURCE = new ResourceLocation(Sustinance.MODID, "textures/gui/driplet.png");
    private static final ResourceLocation DROPLET_RESOURCE = new ResourceLocation(Sustinance.MODID, "textures/gui/droplet.png");

    // TODO: Implement Heart-Rendering
    /**
     * @param event
     */
    @SubscribeEvent
    public static void overrideHeartRendering(RenderGuiOverlayEvent.Pre event) {
        long lastHealthTime = 0L, healthBlinkTime = 0L;
        int displayHealth = 0, lastHealth = 0;
        if (event.isCanceled()
                || client.options.hideGui
                || !event.getOverlay().id().equals(VanillaGuiOverlay.PLAYER_HEALTH.id())
                || !((ForgeGui) client.gui).shouldDrawSurvivalElements()
                || !(client.getCameraEntity() instanceof Player player)) {
            return;
        }

        // client.getProfiler().push("health");
        // TODO: Render Hearts here
        // client.getProfiler().pop();

        //event.setCanceled(true);
    }

    @SubscribeEvent
    public static void hydrationRendering(RenderGuiOverlayEvent.Post event) {
        Optional<Hydration> cap = SustinanceHelper.getHydration(client.player);
        if (cap.isPresent()) {
            Hydration hydration = cap.get();
            int left = client.getWindow().getGuiScaledWidth() / 2 + 10;
            int top = client.getWindow().getGuiScaledHeight() - 49;
            renderHydration(left, top, hydration, event.getGuiGraphics());
        }
    }

    public static void renderHydration(int x, int y, Hydration hydration, GuiGraphics graphics) {
        int currentThirst = Math.floorDiv(hydration.getThirst(), 2);
        boolean shouldRenderDriplet = currentThirst * 2 < hydration.getThirst();
        for (int i = 0; i < currentThirst; i++) {
            int offset = ((10 - currentThirst) * 8) + 8 * i;
            graphics.blit(DROPLET_RESOURCE, x + offset, y, 0, 0, 9, 9, 9, 9);
        }
        if (shouldRenderDriplet) {
            graphics.blit(DRIPLET_RESOURCE, x + ((10 - currentThirst - 1) * 8) + 1, y + 2, 0, 0, 7, 7, 7, 7);
        }
    }

}
