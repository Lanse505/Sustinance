package lanse505.sustinance.client;

import com.mojang.blaze3d.vertex.PoseStack;
import lanse505.sustinance.api.sustinance.nutrition.HeartType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class RenderHelper {

    private static final Minecraft CLIENT = Minecraft.getInstance();

    public static void renderPlayerHearts(PoseStack pose,
                                          Player player, int left, int top,
                                          int healthMax, int health, int displayHealth, int absorption,
                                          boolean highlight) {
    }

    private void renderHeart(GuiGraphics pGuiGraphics, HeartType heartType, int pX, int pY, int pYOffset, boolean pRenderHighlight, boolean pHalfHeart) {

    }
}
