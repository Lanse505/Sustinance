package lanse505.sustinance.common.item.drinkable;

import lanse505.sustinance.api.drinkable.BaseDrinkable;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import org.jetbrains.annotations.Nullable;

public class CanteenDrinkable extends BaseDrinkable {

    public CanteenDrinkable(int thirstModifier, float hydrationModifier) {
        super(thirstModifier, hydrationModifier);
    }

    @Override
    public int getThirstModifier(@Nullable ItemStack consumed) {
        if (consumed != null) {
            IFluidHandlerItem handlerItem = consumed.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElseThrow(() -> new IllegalArgumentException("Can't find IFluidHandlerItem capability for Canteen"));
            FluidStack fluidStack = handlerItem.getFluidInTank(0);
            //TODO: Differenciate out the thirst and hydration values depending on the WaterType. The canteen should only hold water.
            if (fluidStack.getAmount() > 334) {
                handlerItem.drain(333, IFluidHandler.FluidAction.EXECUTE);
                // TODO: Return the appropriate thirstModifier value for the water.
            } else if (fluidStack.getAmount() == 334) {
                handlerItem.drain(334, IFluidHandler.FluidAction.EXECUTE);
                // TODO: Return the appropriate thirstModifier value for the water.
            }
            return 0;
        }
        return super.getThirstModifier(consumed);
    }

    @Override
    public float getHydrationModifier(@Nullable ItemStack consumed) {
        if (consumed != null) {
            IFluidHandlerItem handlerItem = consumed.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElseThrow(() -> new IllegalArgumentException("Can't find IFluidHandlerItem capability for Canteen"));
            FluidStack fluidStack = handlerItem.getFluidInTank(0);
            //TODO: Differenciate out the thirst and hydration values depending on the WaterType. The canteen should only hold water.
            if (fluidStack.getAmount() > 334) {
                handlerItem.drain(333, IFluidHandler.FluidAction.EXECUTE);
                // TODO: Return the appropriate thirstModifier value for the water.
            } else if (fluidStack.getAmount() == 334) {
                handlerItem.drain(334, IFluidHandler.FluidAction.EXECUTE);
                // TODO: Return the appropriate thirstModifier value for the water.
            }
        }
        return super.getHydrationModifier(consumed);
    }

}
