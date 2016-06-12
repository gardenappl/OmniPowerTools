package goldenapple.omnitools;

import goldenapple.omnitools.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;

public class OmniDrillsCreativeTab extends CreativeTabs {
    private ItemStack iconStack;

    public OmniDrillsCreativeTab() {
        super(OmniTools.MOD_ID);
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.darkSteelDrill;
    }

    @Override
    public ItemStack getIconItemStack() {
        if(iconStack == null){
            iconStack = new ItemStack(getTabIconItem());
            iconStack.setTagInfo("CreativeTabIcon", new NBTTagByte((byte) 1));
        }
        return iconStack;
    }

    @Override
    public String getTranslatedTabLabel() {
        return OmniTools.MOD_NAME;
    }
}
