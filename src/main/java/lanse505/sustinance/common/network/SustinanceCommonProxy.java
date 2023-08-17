package lanse505.sustinance.common.network;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class SustinanceCommonProxy {
    public Player getPlayer(NetworkEvent.Context context) {
        return context.getSender();
    }
}
