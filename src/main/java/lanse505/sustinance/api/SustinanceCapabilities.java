package lanse505.sustinance.api;

import lanse505.sustinance.Sustinance;
import lanse505.sustinance.api.sustinance.DataSheet;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SustinanceCapabilities {

    public static final ResourceLocation SUSTINANCE_DATA_KEY = new ResourceLocation(Sustinance.MODID, "sustinance_data");
    public static final Capability<DataSheet> SUSTINANCE_DATA = CapabilityManager.get(new CapabilityToken<>() {});

}
