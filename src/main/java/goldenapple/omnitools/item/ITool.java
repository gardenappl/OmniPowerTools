package goldenapple.omnitools.item;

import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.item.ItemStack;

public interface ITool {
    public ToolProperties getProperties(ItemStack stack);

    public boolean hasDrillingAnimation(ItemStack stack);
}
