package lanse505.sustinance.api.sustinance;

import lanse505.sustinance.api.sustinance.hydration.Hydration;
import lanse505.sustinance.api.sustinance.weight.Weight;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Optional;

public interface DataSheet extends INBTSerializable<CompoundTag> {

    Optional<Hydration> getHydration();
    // TODO: Here goes the Nutrition module
    Optional<Weight> getWeight();

}
