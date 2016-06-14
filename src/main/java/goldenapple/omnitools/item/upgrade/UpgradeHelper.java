package goldenapple.omnitools.item.upgrade;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class UpgradeHelper {
    public static int getUpgradeLevel(ItemStack stack, Upgrade upgrade) {
        if(!stack.hasTagCompound() || stack.getSubCompound("Upgrades", false) == null)
            return 0;

        NBTTagCompound upgrades = stack.getSubCompound("Upgrades", false);
        return upgrades.getByte(upgrade.name);
    }

    public static Map<Upgrade, Byte> getUpgrades(ItemStack stack){
        HashMap<Upgrade, Byte> map = new HashMap<>();

        for(Upgrade upgrade : Upgrades.registry){
            int level = getUpgradeLevel(stack, upgrade);
            if(level > 0)
                map.put(upgrade, (byte)level);
        }

        return map;
    }
}
