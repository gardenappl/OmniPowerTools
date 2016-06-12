package goldenapple.omnitools.item;

import goldenapple.omnitools.item.upgrade.UpgradeType;
import net.minecraft.item.ItemStack;

public interface IUpgradeable {
    public UpgradeType getUpgradeType(ItemStack stack);
}
