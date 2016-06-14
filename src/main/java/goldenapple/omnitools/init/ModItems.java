package goldenapple.omnitools.init;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.ItemChainsawRF;
import goldenapple.omnitools.item.ItemDarkChainsaw;
import goldenapple.omnitools.item.ItemDarkDrill;
import goldenapple.omnitools.item.ItemMultiMetadata;
import goldenapple.omnitools.reference.Names;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {
    public static ItemMultiMetadata materialEIO;
    public static ItemDarkDrill darkSteelDrill1;
    public static ItemDarkDrill darkSteelDrill2;
    public static ItemDarkDrill darkSteelDrill3;
    public static ItemDarkDrill darkSteelDrill4;
    public static ItemChainsawRF darkSteelChainsaw1;
    public static ItemChainsawRF darkSteelChainsaw2;
    public static ItemChainsawRF darkSteelChainsaw3;
    public static ItemChainsawRF darkSteelChainsaw4;

    public static void register(){
        materialEIO = register(new ItemMultiMetadata(Names.MATERIAL_EIO, Names.MATERIALS_EIO), Names.MATERIAL_EIO);
        darkSteelDrill1 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_1, RFToolProperties.DARK_DRILL_1, 0), Names.DARK_STEEL_DRILL);
        darkSteelDrill2 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_2, RFToolProperties.DARK_DRILL_2, 1), Names.DARK_STEEL_DRILL + 2);
        darkSteelDrill3 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_3, RFToolProperties.DARK_DRILL_3, 2), Names.DARK_STEEL_DRILL + 3);
        darkSteelDrill4 = register(new ItemDarkDrill(ToolProperties.DARK_DRILL_4, RFToolProperties.DARK_DRILL_4, 3), Names.DARK_STEEL_DRILL + 4);
        darkSteelChainsaw1 = register(new ItemDarkChainsaw(ToolProperties.DARK_CHAINSAW_1, RFToolProperties.DARK_CHAINSAW_1, Config.darkChainsawAttackSpeed, 0), Names.DARK_STEEL_CHAINSAW);
        darkSteelChainsaw2 = register(new ItemDarkChainsaw(ToolProperties.DARK_CHAINSAW_2, RFToolProperties.DARK_CHAINSAW_2, Config.darkChainsawAttackSpeed, 1), Names.DARK_STEEL_CHAINSAW + 2);
        darkSteelChainsaw3 = register(new ItemDarkChainsaw(ToolProperties.DARK_CHAINSAW_3, RFToolProperties.DARK_CHAINSAW_3, Config.darkChainsawAttackSpeed, 2), Names.DARK_STEEL_CHAINSAW + 3);
        darkSteelChainsaw4 = register(new ItemDarkChainsaw(ToolProperties.DARK_CHAINSAW_4, RFToolProperties.DARK_CHAINSAW_4, Config.darkChainsawAttackSpeed, 3), Names.DARK_STEEL_CHAINSAW + 4);
    }

    public static <T extends Item> T register(T item, String name){
        item.setUnlocalizedName(OmniTools.MOD_ID + ":" + name);
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }
}
