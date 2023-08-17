package lanse505.sustinance.api.sustinance.weight;

import lanse505.sustinance.api.sustinance.weight.modifier.WeightModifier;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

// TODO: Implement an Item/Ingredient -> Weight system
// TODO: Finish implementing the WeightModifier system and figure out how people will be able to add them
// TODO: Implement Events for when we are recalculating maxWeight and weight?
public interface Weight extends INBTSerializable<CompoundTag> {

    int getWeight();
    void setWeight(int weight);
    int getMaxWeight();
    void setMaxWeight(int weight);

    void addWeightModifier(WeightModifier modifier);
    void removeWeightModifier(WeightModifier modifier);
    List<WeightModifier> getAdditionalWeightModifiers();

    boolean isEncumbered();
    boolean isOverencumbered();

}
