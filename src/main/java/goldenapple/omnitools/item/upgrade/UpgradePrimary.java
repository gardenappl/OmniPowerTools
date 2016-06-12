package goldenapple.omnitools.item.upgrade;

import net.minecraft.item.ItemStack;

public abstract class UpgradePrimary extends Upgrade{
    public UpgradePrimary(String name, UpgradeType type, int maxLevel) {
        super(name, type, maxLevel);
    }

    public abstract ItemStack getNewItem(ItemStack stack, int level);
}
