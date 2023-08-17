package lanse505.sustinance.common.network.provider;

import lanse505.sustinance.common.network.SustinanceCommonProxy;
import lanse505.sustinance.server.network.SustinanceServerProxy;
import net.minecraftforge.fml.DistExecutor;

public class SustinanceServerProvider implements DistExecutor.SafeSupplier<SustinanceCommonProxy> {
    @Override
    public SustinanceCommonProxy get() {
        return new SustinanceServerProxy();
    }
}