package goldenapple.omnitools.item.upgrade;

import net.minecraft.item.ItemStack;

public class UpgradeEmpowered extends UpgradePrimary {
    public UpgradeEmpowered() {
        super("empowered", UpgradeType.ALL, 3);
    }

    @Override
    public boolean isRecipeValid(ItemStack stack, int level) {
        switch (level){
            case 1:
                return stack.
        }
    }

    @Override
    public int getLevelCost(ItemStack stack, int level) {
        return 0;
    }

    /** UpgradePrimary */

    @Override
    public ItemStack getNewItem(ItemStack stack, int level) {
        return null;
    }
}
