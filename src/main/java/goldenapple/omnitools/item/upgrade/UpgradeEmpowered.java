package goldenapple.omnitools.item.upgrade;

import goldenapple.omnitools.item.ItemDarkChainsaw;
import goldenapple.omnitools.item.ItemDarkDrill;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class UpgradeEmpowered extends Upgrade {
    ResourceLocation eioCapacitor = new ResourceLocation("EnderIO", "itemBasicCapacitor");
    public UpgradeEmpowered() {
        super("empowered", 3);
    }

    @Override
    public boolean canApply(ItemStack stack, int level) {
        return super.canApply(stack, level) && (stack.getItem() instanceof ItemDarkDrill || stack.getItem() instanceof ItemDarkChainsaw);
    }

    @Override
    public boolean isRecipeValid(ItemStack stack, int level) {
        return stack.getItem() == Item.REGISTRY.getObject(eioCapacitor) && stack.getItemDamage() == level;
    }

    @Override
    public int getLevelCost(ItemStack stack, int level) {
        switch (level){
            case 1: return 15;
            case 2: return 25;
            case 3: return 35;
            default: return 0;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ItemStack apply(ItemStack stack, int level, boolean copy) {
        if(copy)
            stack = stack.copy();
        if(stack.getItem() instanceof ItemDarkDrill)
            stack.setItem(ItemDarkDrill.getEmpoweredUpgrade(level));
        else if(stack.getItem() instanceof ItemDarkChainsaw)
            stack.setItem(ItemDarkChainsaw.getEmpoweredUpgrade(level));
        return super.apply(stack, level, false);
    }
}
