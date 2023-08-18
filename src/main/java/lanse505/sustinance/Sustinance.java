package lanse505.sustinance;

import com.mojang.logging.LogUtils;
import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.sustinance.DataSheet;
import lanse505.sustinance.api.sustinance.DataSheetCapability;
import lanse505.sustinance.client.SustinanceClient;
import lanse505.sustinance.common.network.SustinanceCommonProxy;
import lanse505.sustinance.common.network.SustinanceNetworkHandler;
import lanse505.sustinance.common.network.provider.SustinanceSafeSuppliers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

// TODO: Implement Nutrient/Hunger system
// README: Any and all 'To-Do' objects should go on the base interface object for the relevant mechanic.
// See: lanse505.sustinance.api.sustinance.hydration.Hydration
@Mod(Sustinance.MODID)
public class Sustinance {

    public static final String MODID = "sustinance";

    public static final Logger LOGGER = LogUtils.getLogger();
    public static SustinanceCommonProxy proxy = DistExecutor.safeRunForDist(SustinanceSafeSuppliers.getClientProxy(), SustinanceSafeSuppliers.getServerProxy());
    public static SustinanceNetworkHandler handler = new SustinanceNetworkHandler();

    public Sustinance() {
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        if (FMLEnvironment.dist == Dist.CLIENT) {
            SustinanceClient.initClient();
        }

        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
            //SustinanceServer.initServer();
        }

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SustinanceConfig.init());

        // Forge EventBus
        forgeEventBus.addListener(this::registerSustinanceCapability);
        forgeEventBus.addGenericListener(Entity.class, this::attachSustinanceCapability);

        // Mod EventBus
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        handler.init();
    }

    private void registerSustinanceCapability(RegisterCapabilitiesEvent event) {
        event.register(DataSheet.class);
    }

    private void attachSustinanceCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(SustinanceCapabilities.SUSTINANCE_DATA_KEY, new DataSheetCapability());
        }
    }

}
