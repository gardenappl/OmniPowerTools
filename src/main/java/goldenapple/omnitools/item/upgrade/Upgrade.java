package goldenapple.omnitools.item.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import java.util.List;

@SuppressWarnings("deprecation") //I18n is deprecated by Forge and I can't find an alternative :/
public abstract class Upgrade {
    public String name;
    public int maxLevel;

    public Upgrade(String name, int maxLevel){
        this.name = name;
        this.maxLevel = maxLevel;
    }

    public boolean canApply(ItemStack stack, int level){
        return level <= maxLevel;
    }

    public abstract boolean isRecipeValid(ItemStack stack, int level);

    public abstract int getLevelCost(ItemStack stack, int level);

    public int getItemCost(ItemStack stack, int level){
        return 1;
    }

    public void addDescription(ItemStack stack, List<String> list){
        int i = 1;
        while(I18n.canTranslate("rfdrills.upgrade." + name + ".desc" + UpgradeHelper.getUpgradeLevel(stack, this) + "." + i)){
            list.add(I18n.translateToLocal("rfdrills.upgrade." + name + ".desc" + UpgradeHelper.getUpgradeLevel(stack, this) + "." + i));
            i++;
        }
    }

    public void addRecipeDescription(ItemStack stack, List<String> list){
        list.add(TextFormatting.DARK_AQUA.toString() + TextFormatting.ITALIC + "rfdrills.upgrade." + I18n.translateToLocal(name + ".recipe" + (UpgradeHelper.getUpgradeLevel(stack, this) + 1)));
    }

    public ItemStack apply(ItemStack stack, int level, boolean copy){
        if(copy)
            stack = stack.copy();
        NBTTagCompound upgrades = stack.getSubCompound("Upgrades", true);
        upgrades.setByte(name, (byte) level);
        return stack;
    }
}
