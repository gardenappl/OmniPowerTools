package goldenapple.omnitools.config;

import goldenapple.omnitools.OmniTools;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.Locale;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class Config {
    public static Configuration config;
    public static final int LATEST_CONFIG_VERSION = 1;

    public static final String CATEGORY_ENDERIO = "enderio";

    public static boolean integrateEIO;
    public static boolean miningAnimation;

    public static float darkChainsawSpeed1 = -3.3F;

    public Config(File file){
        if(config == null)
            config = new Configuration(file, Integer.toString(LATEST_CONFIG_VERSION));
        loadConfig();
    }

    private void loadConfig(){
        config.getCategory(CATEGORY_ENDERIO).setLanguageKey("config.category.eio");

        integrateEIO = config.get(CATEGORY_ENDERIO, "Enable EIO Integration", true).setLanguageKey("config.integrateEIO").setRequiresMcRestart(true).getBoolean();
        miningAnimation = config.get(CATEGORY_GENERAL, "Mining Animation", true).setLanguageKey("config.animation").setRequiresWorldRestart(false).getBoolean();

        ToolProperties.DARK_CHAINSAW_1 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw1", 5, 8, 9, 25, false, EnumRarity.UNCOMMON);
        RFToolProperties.DARK_CHAINSAW_1 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw1", 200000, 500, 400);
        darkChainsawSpeed1 = ((float) config.get(getFullCategoryPath(CATEGORY_ENDERIO, "dark_chainsaw1"), "Attack Speed", darkChainsawSpeed1 + 4.0f).setLanguageKey("config.attackSpeed").setRequiresMcRestart(true).getDouble()) - 4.0f;

        if(config.hasChanged())
            config.save();
    }

    private static ToolProperties getToolProperties(String category, String name, int miningLevel, float efficiency, float damage, int enchant, boolean canBreak, EnumRarity rarity, MiningMode... modes){
        miningLevel = config.get(getFullCategoryPath(category, name), "Mining Level", miningLevel).setLanguageKey("config.miningLevel").setRequiresMcRestart(true).getInt();
        efficiency = (float)config.get(getFullCategoryPath(category, name), "Efficiency", efficiency).setLanguageKey("config.efficiency").setRequiresMcRestart(true).getDouble();
        damage = (float)config.get(getFullCategoryPath(category, name), "Damage", damage).setLanguageKey("config.damage").setRequiresMcRestart(true).getDouble();
        enchant = config.get(getFullCategoryPath(category, name), "Enchantability", enchant).setLanguageKey("config.enchant").setRequiresMcRestart(true).getInt();
        canBreak = config.get(getFullCategoryPath(category, name), "Can Break", canBreak).setLanguageKey("config.canBreak").setRequiresMcRestart(true).getBoolean();

        Item.ToolMaterial material = EnumHelper.addToolMaterial(OmniTools.MOD_ID + ":" + name.toUpperCase(Locale.ROOT), miningLevel, 9000, efficiency, damage, enchant);
        return new ToolProperties(material, canBreak, rarity, modes);
    }

    private static RFToolProperties getToolPropertiesRF(String category, String name, int maxEnergy, int rechargeRate, int energyPerBlock){
        maxEnergy = config.get(getFullCategoryPath(category, name), "Maximum Energy", maxEnergy).setLanguageKey("config.maxEnergy").setRequiresMcRestart(true).getInt();
        energyPerBlock = config.get(getFullCategoryPath(category, name), "Energy Per Block", energyPerBlock).setLanguageKey("config.energyPerBlock").setRequiresMcRestart(true).getInt();
        rechargeRate = config.get(getFullCategoryPath(category, name), "Recharge Rate", rechargeRate).setLanguageKey("config.rechargeRate").setRequiresMcRestart(true).getInt();

        return new RFToolProperties(maxEnergy, rechargeRate, energyPerBlock);
    }

    private static String getFullCategoryPath(String... hierarchy){
        return String.join(Configuration.CATEGORY_SPLITTER, hierarchy);
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
        if(event.getModID().equals(OmniTools.MOD_ID))
            loadConfig();
    }
}
