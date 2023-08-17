package lanse505.sustinance.server.network;

import lanse505.sustinance.common.network.SustinanceCommonProxy;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class SustinanceServerProxy extends SustinanceCommonProxy {
    @Override
    public Player getPlayer(NetworkEvent.Context context) {
        return super.getPlayer(context);
    }
}
