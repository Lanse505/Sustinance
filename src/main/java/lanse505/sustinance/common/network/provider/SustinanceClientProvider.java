package lanse505.sustinance.common.network.provider;

import lanse505.sustinance.client.SustinanceClientProxy;
import lanse505.sustinance.common.network.SustinanceCommonProxy;
import net.minecraftforge.fml.DistExecutor;

public class SustinanceClientProvider implements DistExecutor.SafeSupplier<SustinanceCommonProxy> {
    @Override
    public SustinanceCommonProxy get() {
        return new SustinanceClientProxy();
    }
}
