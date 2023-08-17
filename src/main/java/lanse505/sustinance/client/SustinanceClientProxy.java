package lanse505.sustinance.client;

import lanse505.sustinance.common.network.SustinanceCommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class SustinanceClientProxy extends SustinanceCommonProxy {

    @Override
    public Player getPlayer(NetworkEvent.Context context) {
        return Minecraft.getInstance().player;
    }
}
