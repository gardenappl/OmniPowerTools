package goldenapple.omnitools.config;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

public class ToolProperties {
    public static ToolProperties DARK_DRILL_1;
    public static ToolProperties DARK_DRILL_2;
    public static ToolProperties DARK_DRILL_3;
    public static ToolProperties DARK_CHAINSAW_1;
    public static ToolProperties DARK_CHAINSAW_2;
    public static ToolProperties DARK_CHAINSAW_3;

    public Item.ToolMaterial material;
    public boolean canBreak;
    public EnumRarity rarity;

    public ToolProperties(Item.ToolMaterial material, boolean canBreak, EnumRarity rarity){
        this.material = material;
        this.canBreak = canBreak;
        this.rarity = rarity;
    }
}
