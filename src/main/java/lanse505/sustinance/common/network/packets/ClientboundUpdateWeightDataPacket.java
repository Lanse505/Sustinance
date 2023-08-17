package lanse505.sustinance.common.network.packets;

import lanse505.sustinance.api.network.INetworkPacket;
import lanse505.sustinance.api.sustinance.SustinanceHelper;
import lanse505.sustinance.api.sustinance.weight.Weight;
import lanse505.sustinance.common.network.SustinanceNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;

public record ClientboundUpdateWeightDataPacket(int weight, int maxWeight) implements INetworkPacket<ClientboundUpdateWeightDataPacket> {

    public ClientboundUpdateWeightDataPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    public static ClientboundUpdateWeightDataPacket decode(FriendlyByteBuf buf) {
        return new ClientboundUpdateWeightDataPacket(buf);
    }

    @Override
    public void handle(ClientboundUpdateWeightDataPacket msg, NetworkEvent.Context context) {
        Player player = SustinanceNetworkHandler.getPlayer(context);
        if (player instanceof ServerPlayer) return;
        context.enqueueWork(() -> {
            Optional<Weight> cap = SustinanceHelper.getWeight(player);
            if (cap.isPresent()) {
                Weight weight = cap.get();
                weight.setWeight(this.weight);
                weight.setMaxWeight(this.maxWeight);
            }
        });
        context.setPacketHandled(true);
    }

    @Override
    public void encode(ClientboundUpdateWeightDataPacket msg, FriendlyByteBuf buffer) {
        buffer.writeInt(msg.weight);
        buffer.writeInt(msg.maxWeight);
    }
}
