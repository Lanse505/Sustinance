package lanse505.sustinance.common.item;

import lanse505.sustinance.api.drinkable.Drinkable;
import lanse505.sustinance.api.item.ItemDrink;
import lanse505.sustinance.common.item.drinkable.CanteenDrinkable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CanteenItem extends ItemDrink {

    protected static final Drinkable CANTEEN_DRINKABLE = new CanteenDrinkable(3, 4.5f);

    protected final int capacity;
    protected final Drinkable drinkable;

    public CanteenItem(Properties properties) {
        super(properties);
        this.capacity = 1000;
        this.drinkable = CANTEEN_DRINKABLE;
    }

    public CanteenItem(Properties properties, int capacity) {
        super(properties);
        this.capacity = capacity;
        this.drinkable = CANTEEN_DRINKABLE;
    }

    public CanteenItem(Properties properties, int capacity, @NotNull Drinkable drinkable) {
        super(properties, drinkable);
        this.capacity = capacity;
        this.drinkable = drinkable;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidHandlerItemStack(stack, capacity);
    }
}
