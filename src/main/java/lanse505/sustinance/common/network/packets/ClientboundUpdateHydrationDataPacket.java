package lanse505.sustinance.common.network.packets;

import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.network.INetworkPacket;
import lanse505.sustinance.api.sustinance.SustinanceHelper;
import lanse505.sustinance.api.sustinance.hydration.Hydration;
import lanse505.sustinance.common.network.SustinanceNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;

public record ClientboundUpdateHydrationDataPacket(int thirst, int lastThirst, float hydration) implements INetworkPacket<ClientboundUpdateHydrationDataPacket> {

    public ClientboundUpdateHydrationDataPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readFloat());
    }

    public static ClientboundUpdateHydrationDataPacket decode(FriendlyByteBuf buf) {
        return new ClientboundUpdateHydrationDataPacket(buf);
    }

    @Override
    public void handle(ClientboundUpdateHydrationDataPacket msg, NetworkEvent.Context context) {
        Player player = SustinanceNetworkHandler.getPlayer(context);
        if (player instanceof ServerPlayer) return;
        context.enqueueWork(() -> {
            Optional<Hydration> cap = SustinanceHelper.getHydration(player);
            if (cap.isPresent()) {
                Hydration hydration = cap.get();
                hydration.setThirst(this.thirst);
                hydration.setLastThirst(this.lastThirst);
                hydration.setHydration(this.hydration);
            }
        });
        context.setPacketHandled(true);
    }

    @Override
    public void encode(ClientboundUpdateHydrationDataPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.thirst);
        buffer.writeInt(msg.lastThirst);
        buffer.writeFloat(msg.hydration);
    }
}
