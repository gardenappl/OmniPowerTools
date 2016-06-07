package goldenapple.omnitools.item;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/** Just IEnergyContainerItem with some methods for convenience */

public interface IRFContainerItem extends IEnergyContainerItem {
    public ItemStack setEnergy(ItemStack stack, int energy);

    public ItemStack drainEnergy(ItemStack stack, int energy, EntityLivingBase entity);
}
