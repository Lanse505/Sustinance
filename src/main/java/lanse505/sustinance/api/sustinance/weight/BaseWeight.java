package lanse505.sustinance.api.sustinance.weight;

import lanse505.sustinance.SustinanceConfig;
import lanse505.sustinance.api.sustinance.weight.modifier.WeightModifier;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class BaseWeight implements Weight {

    public static final int MIN_WEIGHT_CONST = 0;
    public static final int DEFAULT_MAX_WEIGHT = 2372;

    private int weight;
    private int maxWeight;

    private boolean shouldUpdateMaxWeight;
    private final List<WeightModifier> weightModifiers;

    public BaseWeight() {
        this.weight = 0;
        this.maxWeight = SustinanceConfig.getInstance().weightConfig.getDefaultMaxWeight();
        this.shouldUpdateMaxWeight = true;
        this.weightModifiers = new ArrayList<>();
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = Math.max(Math.min(weight, maxWeight), MIN_WEIGHT_CONST);
    }

    @Override
    public int getMaxWeight() {
        if (shouldUpdateMaxWeight) {
            int maxWeight = SustinanceConfig.getInstance().weightConfig.getDefaultMaxWeight();
            this.shouldUpdateMaxWeight = false;
            for (WeightModifier modifier : getAdditionalWeightModifiers()) {
                maxWeight = Math.max(modifier.getModifiedWeight(maxWeight), MIN_WEIGHT_CONST);
            }
            return this.maxWeight = maxWeight;
        }
        return this.maxWeight;
    }

    @Override
    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Override
    public void addWeightModifier(WeightModifier modifier) {
        this.weightModifiers.add(modifier);
        this.shouldUpdateMaxWeight = true;
    }

    @Override
    public void removeWeightModifier(WeightModifier modifier) {
        this.weightModifiers.remove(modifier);
        this.shouldUpdateMaxWeight = true;
    }

    @Override
    public List<WeightModifier> getAdditionalWeightModifiers() {
        return this.weightModifiers;
    }

    @Override
    public boolean isEncumbered() {
        return isOverencumbered() || this.weight >= Math.round(this.maxWeight * 0.75f);
    }

    @Override
    public boolean isOverencumbered() {
        return this.weight >= Math.round(this.maxWeight * 0.9f);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag weightData = new CompoundTag();
        weightData.putInt("weight", this.weight);
        weightData.putInt("maxWeight", this.maxWeight);
        weightData.putBoolean("shouldUpdateMaxWeight", this.shouldUpdateMaxWeight);
        return weightData;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.weight = nbt.getInt("weight");
        this.maxWeight = nbt.getInt("maxWeight");
        this.shouldUpdateMaxWeight = nbt.getBoolean("shouldUpdateMaxWeight");
    }
}
