package lanse505.sustinance.api.sustinance.nutrition;

import lanse505.sustinance.Sustinance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public enum HeartType {

    ORANGE(0,0, 0, 10), ORANGE_HC(0, 20, 0, 30),
    YELLOW(10, 0, 10, 10), YELLOW_HC(10, 20, 10, 30),
    LIME(20, 0, 20, 10), LIME_HC(20, 20, 20, 30),
    GREEN(30, 0, 30, 10), GREEN_HC(30, 20, 30, 30),
    TEAL(40, 0, 40, 10), TEAL_HC(40, 20, 40, 30),
    BLUE(50, 0, 50, 10), BLUE_HC(50, 20, 50, 30);

    public static final ResourceLocation HEART_TEXTURE = new ResourceLocation(Sustinance.MODID, "textures/gui/hearts.png");

    private final int fullX;
    private final int fullY;
    private final int halfX;
    private final int halfY;

    HeartType(int fullX, int fullY, int halfX, int halfY) {
        this.fullX = fullX;
        this.fullY = fullY;
        this.halfX = halfX;
        this.halfY = halfY;
    }

    public int getFullX() {
        return fullX;
    }

    public int getFullY() {
        return fullY;
    }

    public int getHalfX() {
        return halfX;
    }

    public int getHalfY() {
        return halfY;
    }

    public int getTextureHeight() {
        return 9;
    }

    public int getTextureWidth() {
        return 9;
    }

    public enum VanillaHeartTypeProxy {
        NORMAL(2, true),
        POISIONED(4, true),
        WITHERED(6, true),
        ABSORBING(8, false),
        FROZEN(9, false);

        private final int index;
        private final boolean canBlink;

        VanillaHeartTypeProxy(int pIndex, boolean pCanBlink) {
            this.index = pIndex;
            this.canBlink = pCanBlink;
        }

        public int getX(boolean pHalfHeart, boolean pRenderHighlight) {
            int i = pHalfHeart ? 1 : 0;
            int j = this.canBlink && pRenderHighlight ? 2 : 0;
            int k = i + j;
            return 16 + (this.index * 2 + i) * 9;
        }

        public static VanillaHeartTypeProxy forPlayer(Player pPlayer) {
            VanillaHeartTypeProxy gui$hearttype;
            if (pPlayer.hasEffect(MobEffects.POISON)) {
                gui$hearttype = POISIONED;
            } else if (pPlayer.hasEffect(MobEffects.WITHER)) {
                gui$hearttype = WITHERED;
            } else if (pPlayer.isFullyFrozen()) {
                gui$hearttype = FROZEN;
            } else {
                gui$hearttype = NORMAL;
            }

            return gui$hearttype;
        }
    }

}
