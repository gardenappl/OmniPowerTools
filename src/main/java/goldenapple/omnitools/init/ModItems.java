package goldenapple.omnitools.init;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.ItemChainsaw;
import goldenapple.omnitools.reference.Names;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemChainsaw darkSteelChainsaw;

    public static void register(){
        darkSteelChainsaw = register(new ItemChainsaw(new ToolProperties(EnumHelper.addToolMaterial("I", 5, 5, 5, 5, 5), false, EnumRarity.COMMON), 5.0f), Names.DARK_STEEL_CHAINSAW);
    }

    public static <T extends Item> T register(T item, String name){
        item.setUnlocalizedName(OmniTools.MOD_ID + ":" + name);
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }
}
