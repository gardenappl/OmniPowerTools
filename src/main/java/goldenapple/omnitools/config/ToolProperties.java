package goldenapple.omnitools.config;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;

public class ToolProperties {

    public static ToolProperties DARK_CHAINSAW_1;

    public Item.ToolMaterial material;
    public boolean canBreak;
    public MiningMode[] modes;
    public EnumRarity rarity;

    public ToolProperties(Item.ToolMaterial material, boolean canBreak, EnumRarity rarity, MiningMode... modes){
        this.material = material;
        this.canBreak = canBreak;
        this.modes = modes;
        this.rarity = rarity;
    }
}
