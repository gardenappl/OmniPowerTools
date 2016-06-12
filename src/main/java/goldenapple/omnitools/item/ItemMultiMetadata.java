package goldenapple.omnitools.item;


import goldenapple.omnitools.OmniTools;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMultiMetadata extends Item {
    private String[] names;

    public ItemMultiMetadata(String name, String[] names){
        this.names = names;
        this.setHasSubtypes(true);
        this.setCreativeTab(OmniTools.creativeTab);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void getSubItems(Item item, CreativeTabs tab, List subItems) {
        for(int i = 0; i < names.length; i++)
            subItems.add(new ItemStack(item, 1, i));
    }

    @Override
    public int getMetadata(int meta) {
        return meta < names.length ? meta : 0;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return stack.getItemDamage() < names.length ? "item." + OmniTools.MOD_ID + ":" + names[stack.getItemDamage()] : getUnlocalizedName();
    }
}
