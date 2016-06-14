package goldenapple.omnitools.item;

import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.upgrade.Upgrades;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemDarkChainsaw extends ItemChainsawRF {
    private static ItemDarkChainsaw[] upgrades = new ItemDarkChainsaw[4];
    private int level;

    public ItemDarkChainsaw(ToolProperties properties, RFToolProperties propertiesRF, float speed, int empoweredLevel) {
        super(properties, propertiesRF, speed);
        upgrades[empoweredLevel] = this;
        level = empoweredLevel;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        if(level <= 0)
            super.getSubItems(item, tab, list);
        else if(level >= upgrades.length - 1){
            list.add(Upgrades.empowered.apply(new ItemStack(item), upgrades.length - 1, false));
            list.add(Upgrades.empowered.apply(setEnergy(new ItemStack(item), propertiesRF.maxEnergy), upgrades.length - 1, false));
        }
    }

    public static ItemDarkChainsaw getEmpoweredUpgrade(int level){
        if(level < 0)
            return upgrades[0];
        else if(level >= upgrades.length)
            return upgrades[upgrades.length - 1];
        return upgrades[level];
    }
}
