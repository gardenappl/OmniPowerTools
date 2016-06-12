package goldenapple.omnitools.item.upgrade;

import goldenapple.omnitools.item.IUpgradeable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UpgradeHelper {
    public static int getUpgradeLevel(ItemStack stack, Upgrade upgrade) {
        if(!(stack.getItem() instanceof IUpgradeable))
        if(!stack.hasTagCompound() || stack.getSubCompound("Upgrades", false) == null)
            return 0;

        NBTTagCompound upgrades = stack.getSubCompound("Upgrades", false);
        return upgrades.getByte(upgrade.name);
    }

    public static ItemStack applyUpgrade(ItemStack stack, Upgrade upgrade, int level) {
        if(upgrade instanceof UpgradePrimary)
            return ((UpgradePrimary) upgrade).getNewItem(stack, level);
        ItemStack upgradedStack = stack.copy();
        NBTTagCompound upgrades = upgradedStack.getSubCompound("Upgrades", true);
        upgrades.setByte(upgrade.name, (byte) level);
        return upgradedStack;
    }

    public static boolean canApply(ItemStack stack, Upgrade upgrade, int level){
        if(!(stack.getItem() instanceof IUpgradeable))
            return false;
        if(upgrade.type != UpgradeType.ALL && ((IUpgradeable) stack.getItem()).getUpgradeType(stack) != upgrade.type)
            return false;
        return level <= upgrade.maxLevel;
    }
}
