package lanse505.sustinance.client;

import lanse505.sustinance.Sustinance;
import net.minecraft.client.Minecraft;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class SustinanceClient {

    public static void initClient() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(SustinanceClient::onClientSetup);
    }

    public static void onClientSetup(FMLClientSetupEvent event)
    {

    }

}
