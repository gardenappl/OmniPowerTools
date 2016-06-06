package goldenapple.omnitools.item;

import goldenapple.omnitools.config.RFToolProperties;
import net.minecraft.item.ItemStack;

public interface IRFTool extends ITool {
    public RFToolProperties getPropertiesRF(ItemStack stack);

    public ItemStack setEnergy(ItemStack stack, int energy);

    public ItemStack drainEnergy(ItemStack stack, int energy);
}
