package goldenapple.omnitools.init;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.config.MiningMode;
import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.ItemChainsaw;
import goldenapple.omnitools.item.ItemChainsawRF;
import goldenapple.omnitools.item.ItemDrill;
import goldenapple.omnitools.reference.Names;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemChainsaw darkSteelChainsaw;
    public static ItemDrill darkSteelDrill;

    public static void register(){
        darkSteelChainsaw = register(new ItemChainsawRF(ToolProperties.DARK_CHAINSAW_1, RFToolProperties.DARK_CHAINSAW_1, Config.darkChainsawSpeed1), Names.DARK_STEEL_CHAINSAW);
        darkSteelDrill = register(new ItemDrill(new ToolProperties(Item.ToolMaterial.DIAMOND, true, EnumRarity.COMMON, new MiningMode(1, 1, 0))), Names.DARK_STEEL_DRILL);
    }

    public static <T extends Item> T register(T item, String name){
        item.setUnlocalizedName(OmniTools.MOD_ID + ":" + name);
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }
}
