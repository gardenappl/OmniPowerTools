package goldenapple.omnitools.item.upgrade;

import goldenapple.omnitools.init.ModItems;
import goldenapple.omnitools.item.ItemDarkChainsaw;
import goldenapple.omnitools.item.ItemDarkDrill;
import goldenapple.omnitools.reference.Metadata;
import net.minecraft.item.ItemStack;

public class UpgradeBeastMode extends Upgrade {
    public UpgradeBeastMode() {
        super("beast_mode", 1);
    }

    @Override
    public boolean canApply(ItemStack stack, int level) {
        if(stack.getItem() instanceof ItemDarkDrill || stack.getItem() instanceof ItemDarkChainsaw)
            return super.canApply(stack, level) && UpgradeHelper.getUpgradeLevel(stack, Upgrades.empowered) >= 2;
        return false;
    }

    @Override
    public boolean isRecipeValid(ItemStack stack, int level) {
        return stack.getItem() == ModItems.materialEIO && stack.getItemDamage() == Metadata.RESONATING_CRYSTAL;
    }

    @Override
    public int getLevelCost(ItemStack stack, int level) {
        return 15;
    }
}
