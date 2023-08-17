package lanse505.sustinance;

import lanse505.sustinance.api.sustinance.weight.BaseWeight;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Sustinance.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SustinanceConfig {

    private ForgeConfigSpec configSpec;
    private static SustinanceConfig configInstance;

    public static ForgeConfigSpec init() {
        configInstance = new SustinanceConfig(new ForgeConfigSpec.Builder());
        return configInstance.getConfigSpec();
    }

    public static SustinanceConfig getInstance() {
        if (configInstance != null) return configInstance;
        throw new IllegalStateException("What the actual.... the config is null?");
    }

    public ForgeConfigSpec getConfigSpec() {
        return configSpec;
    }

    private final ForgeConfigSpec.BooleanValue isHydrationEnabled;

    private final ForgeConfigSpec.BooleanValue isNutritionEnabled;

    private final ForgeConfigSpec.BooleanValue isWeightEnabled;

    public boolean enableHydration;

    public boolean enableNutrition;

    public boolean enableWeight;

    public HydrationConfig hydrationConfig;

    public NutritionConfig nutritionConfig;

    public WeightConfig weightConfig;

    public SustinanceConfig(ForgeConfigSpec.Builder builder) {
        builder.push("General");
        this.isHydrationEnabled = builder.comment("Enable the 'Hydration' mechanic").define("enableThirst", true);
        this.isNutritionEnabled = builder.comment("Enable the 'Nutrition' mechanic").define("enableNutrition", true);
        this.isWeightEnabled = builder.comment("Enable the 'Weight'").define("enableWeight", true);
        builder.pop();
        this.hydrationConfig = new HydrationConfig(builder);
        this.nutritionConfig = new NutritionConfig(builder);
        this.weightConfig = new WeightConfig(builder);
        configSpec = builder.build();
    }

    public ForgeConfigSpec.BooleanValue getHydrationModuleEnabled() {
        return isHydrationEnabled;
    }

    public ForgeConfigSpec.BooleanValue getNutritionModuleEnabled() {
        return isNutritionEnabled;
    }

    public ForgeConfigSpec.BooleanValue getWeightModuleEnabled() {
        return isWeightEnabled;
    }

    public static class HydrationConfig {
        public HydrationConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Hydration");

            builder.pop();
        }
    }

    public static class NutritionConfig {

        public NutritionConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Nutrition");

            builder.pop();
        }
    }

    public static class WeightConfig {

        private final ForgeConfigSpec.IntValue defaultMaxWeightConfig;
        private final ForgeConfigSpec.IntValue defaultUpdateDelayInTicksConfig;
        private int defaultMaxWeight;
        private int defaultUpdateDelayInTicks;

        public WeightConfig(ForgeConfigSpec.Builder builder) {
            builder.push("Weight");
            this.defaultMaxWeightConfig = builder.comment("Default max weight").defineInRange("maxDefaultWeight", BaseWeight.DEFAULT_MAX_WEIGHT, BaseWeight.MIN_WEIGHT_CONST, Integer.MAX_VALUE);
            this.defaultUpdateDelayInTicksConfig = builder.comment("Default update delay in ticks").defineInRange("defaultUpdateDelayInTicks", 10, 1, 100);
            builder.pop();
        }

        public int getDefaultMaxWeight() {
            return defaultMaxWeight;
        }

        public int getDefaultUpdateDelayInTicks() {
            return defaultUpdateDelayInTicks;
        }
    }

    @SubscribeEvent
    public static void onConfigLoad(final ModConfigEvent event)
    {
        SustinanceConfig config = SustinanceConfig.getInstance();
        config.enableHydration = config.getHydrationModuleEnabled().get();
        config.enableNutrition = config.getNutritionModuleEnabled().get();
        config.enableWeight = config.getWeightModuleEnabled().get();

        if (config.enableHydration) {
            SustinanceConfig.HydrationConfig hydrationConfig = config.hydrationConfig;

        }

        if (config.enableNutrition) {
            SustinanceConfig.NutritionConfig nutritionConfig = config.nutritionConfig;

        }

        if (config.enableWeight) {
            SustinanceConfig.WeightConfig weightConfig = config.weightConfig;
            weightConfig.defaultMaxWeight = weightConfig.defaultMaxWeightConfig.get();
            weightConfig.defaultUpdateDelayInTicks = weightConfig.defaultUpdateDelayInTicksConfig.get();
        }
    }
}
