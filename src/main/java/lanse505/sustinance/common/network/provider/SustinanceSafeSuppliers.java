package lanse505.sustinance.common.network.provider;

import lanse505.sustinance.client.SustinanceClientProxy;
import lanse505.sustinance.common.network.SustinanceCommonProxy;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.Supplier;

public class SustinanceSafeSuppliers {
    public static Supplier<DistExecutor.SafeSupplier<SustinanceCommonProxy>> getClientProxy() {
        return SustinanceClientProvider::new;
    }

    public static Supplier<DistExecutor.SafeSupplier<SustinanceCommonProxy>> getServerProxy() {
        return SustinanceServerProvider::new;
    }
}
