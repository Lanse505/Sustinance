package lanse505.sustinance.api;

import lanse505.sustinance.api.hydration.Hydration;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SustinanceCapabilities {

    public static final Capability<Hydration> HYDRATION = CapabilityManager.get(new CapabilityToken<>() {});



}
