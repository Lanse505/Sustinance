package lanse505.sustinance.api.sustinance;

import lanse505.sustinance.SustinanceConfig;
import lanse505.sustinance.api.SustinanceCapabilities;
import lanse505.sustinance.api.sustinance.hydration.BaseHydration;
import lanse505.sustinance.api.sustinance.hydration.Hydration;
import lanse505.sustinance.api.sustinance.weight.BaseWeight;
import lanse505.sustinance.api.sustinance.weight.Weight;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DataSheetCapability implements DataSheet, ICapabilitySerializable<CompoundTag> {

    private final LazyOptional<DataSheet> holder = LazyOptional.of(() -> this);

    private Optional<Hydration> hydration = Optional.empty();
    private Optional<Weight> weight = Optional.empty();

    public DataSheetCapability() {
        if (SustinanceHelper.isHydrationEnabled()) this.hydration = Optional.of(new BaseHydration());
        if (SustinanceHelper.isWeightEnabled()) this.weight = Optional.of(new BaseWeight());
    }

    @Override
    public Optional<Hydration> getHydration() {
        return this.hydration;
    }

    @Override
    public Optional<Weight> getWeight() {
        return this.weight;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        if (SustinanceHelper.isHydrationEnabled() && this.hydration.isPresent())
            tag.put("Hydration", this.hydration.get().serializeNBT());
        if (SustinanceHelper.isWeightEnabled() && this.weight.isPresent())
            tag.put("Weight", this.weight.get().serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (SustinanceHelper.isHydrationEnabled() && this.hydration.isPresent())
            this.hydration.get().deserializeNBT(nbt.getCompound("Hydration"));
        if (SustinanceHelper.isWeightEnabled() && this.weight.isPresent())
            this.weight.get().deserializeNBT(nbt.getCompound("Weight"));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @org.jetbrains.annotations.Nullable Direction side) {
        return cap == SustinanceCapabilities.SUSTINANCE_DATA
                ? SustinanceCapabilities.SUSTINANCE_DATA.orEmpty(cap, this.holder)
                : LazyOptional.empty();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Sustinance");
        SustinanceConfig config = SustinanceConfig.getInstance();
        if (config.enableHydration) {
            output.append("  Hydration")
                    .append("    Thirst: ").append(this.hydration.get().getThirst())
                    .append("    Last Thirst: ").append(this.hydration.get().getLastThirst())
                    .append("    Hydration: ").append(this.hydration.get().getHydrationLevel());
        }

        if (config.enableHydration) {
            output.append("  Weight")
                    .append("    Weight: ").append(this.weight.get().getWeight())
                    .append("    Max Weight: ").append(this.weight.get().getMaxWeight())
                    .append("    Is Encumbered: ").append(this.weight.get().isEncumbered())
                    .append("    Is Overencumbered: ").append(this.weight.get().isOverencumbered());
        }

        return output.toString();
    }
}
