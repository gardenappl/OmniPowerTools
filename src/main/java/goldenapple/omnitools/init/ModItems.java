package goldenapple.omnitools.init;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.ItemChainsawRF;
import goldenapple.omnitools.item.ItemDarkDrill;
import goldenapple.omnitools.item.ItemMultiMetadata;
import goldenapple.omnitools.reference.Names;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemMultiMetadata materialEIO;
    public static ItemChainsawRF darkSteelChainsaw1;
    public static ItemDarkDrill darkSteelDrill1;
//    public static ItemChainsawRF darkSteelChainsaw2;
    public static ItemDarkDrill darkSteelDrill2;
//    public static ItemChainsawRF darkSteelChainsaw3;
    public static ItemDarkDrill darkSteelDrill3;

    public static void register(){
        materialEIO = register(new ItemMultiMetadata(Names.MATERIAL_EIO, Names.MATERIALS_EIO), Names.MATERIAL_EIO);
        darkSteelChainsaw1 = register(new ItemChainsawRF(ToolProperties.DARK_CHAINSAW_1, RFToolProperties.DARK_CHAINSAW_1, Config.darkChainsawAttackSpeed), Names.DARK_STEEL_CHAINSAW);
        darkSteelDrill1 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_1, RFToolProperties.DARK_DRILL_1), Names.DARK_STEEL_DRILL);
//        darkSteelChainsaw2 = register(new ItemChainsawRF(ToolProperties.DARK_CHAINSAW_1, RFToolProperties.DARK_CHAINSAW_1, Config.darkChainsawAttackSpeed), Names.DARK_STEEL_CHAINSAW);
        darkSteelDrill2 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_2, RFToolProperties.DARK_DRILL_2), Names.DARK_STEEL_DRILL + 2);
//        darkSteelChainsaw1 = register(new ItemChainsawRF(ToolProperties.DARK_CHAINSAW_1, RFToolProperties.DARK_CHAINSAW_1, Config.darkChainsawAttackSpeed), Names.DARK_STEEL_CHAINSAW);
        darkSteelDrill3 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_3, RFToolProperties.DARK_DRILL_3), Names.DARK_STEEL_DRILL + 3);
    }

    public static <T extends Item> T register(T item, String name){
        item.setUnlocalizedName(OmniTools.MOD_ID + ":" + name);
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }
}
