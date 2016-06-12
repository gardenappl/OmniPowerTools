package goldenapple.omnitools.item;

import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.upgrade.UpgradeType;
import net.minecraft.item.ItemStack;

public class ItemDarkDrill extends ItemDrillRF implements IUpgradeable {
    public ItemDarkDrill(ToolProperties properties, RFToolProperties propertiesRF) {
        super(properties, propertiesRF);
    }

    @Override
    public UpgradeType getUpgradeType(ItemStack stack) {
        return UpgradeType.DRILL;
    }

    //    public MiningMode getMode(ItemStack stack) {
//        if (modes.length == 1 || !stack.hasTagCompound())
//            return modes[0];
//        if (stack.getTagCompound().getByte("Mode") >= modes.length)
//            return modes[0];
//        else
//            return modes[stack.getTagCompound().getByte("Mode")];
//    }
//
//    @Override
//    public boolean hasAoE(ItemStack stack, EntityPlayer player) {
//        return canMine(stack) && getMode(stack) != MiningMode.NORMAL;
//    }
}
