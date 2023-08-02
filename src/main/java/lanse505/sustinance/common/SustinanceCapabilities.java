package lanse505.sustinance.common;

import lanse505.sustinance.common.thirst.drinkable.DrinkableData;
import lanse505.sustinance.common.thirst.hydration.HydrationData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SustinanceCapabilities {

    public static final Capability<HydrationData> HYDRATION = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Capability<DrinkableData> DRINKABLE = CapabilityManager.get(new CapabilityToken<>() {});



}
