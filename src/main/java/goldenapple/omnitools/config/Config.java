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
    public static int miningAnimation;
    private static final String[] MINING_ANIMATIONS = new String[]{"false", "third-person-only", "true"};

    public static float darkChainsawAttackSpeed = -3f;
    public static float drillAttackSpeed = -2.6f;

    public Config(File file){
        if(config == null)
            config = new Configuration(file, Integer.toString(LATEST_CONFIG_VERSION));
        loadConfig();
    }

    private void loadConfig(){
        config.getCategory(CATEGORY_ENDERIO).setLanguageKey("config.category.eio");

        integrateEIO = config.get(CATEGORY_ENDERIO, "Enable EIO Integration", true).setLanguageKey("config.integrateEIO").setRequiresMcRestart(true).getBoolean();
        String anim = config.get(CATEGORY_GENERAL, "Mining Animation", MINING_ANIMATIONS[2]).setLanguageKey("config.animation").setValidValues(MINING_ANIMATIONS).setRequiresWorldRestart(false).getString();
        for(int i = 0; i < MINING_ANIMATIONS.length; i++)
            if(MINING_ANIMATIONS[i].equals(anim))
                miningAnimation = i;

        drillAttackSpeed = (float)config.get(CATEGORY_GENERAL, "Drill Attack Speed", drillAttackSpeed + 4).setLanguageKey("config.attackSpeedDrills").setRequiresMcRestart(true).getDouble() - 4;
        darkChainsawAttackSpeed = (float)config.get(getFullCategoryPath(CATEGORY_ENDERIO, "dark_chainsaw1"), "Attack Speed", darkChainsawAttackSpeed + 4).setLanguageKey("config.attackSpeed").setRequiresMcRestart(true).getDouble() - 4;

        ToolProperties.DARK_DRILL_1 = getToolProperties(CATEGORY_ENDERIO, "dark_drill1", 2, 6, 3, 25, true, EnumRarity.COMMON);
        RFToolProperties.DARK_DRILL_1 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill1", 200000, 800, 500); //125 uses
        ToolProperties.DARK_DRILL_2 = getToolProperties(CATEGORY_ENDERIO, "dark_drill2", 3, 8, 4, 25, false, EnumRarity.COMMON);
        RFToolProperties.DARK_DRILL_2 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill2", 1000000, 1600, 2000); //625 uses
        ToolProperties.DARK_DRILL_3 = getToolProperties(CATEGORY_ENDERIO, "dark_drill3", 5, 10, 5, 25, false, EnumRarity.UNCOMMON);
        RFToolProperties.DARK_DRILL_3 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill3", 5000000, 2000, 5000); //2500 uses

        ToolProperties.DARK_CHAINSAW_1 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw1", 2, 6, 7, 25, true, EnumRarity.COMMON);
        RFToolProperties.DARK_CHAINSAW_1 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw1", 200000, 800, 500); //125 uses
        ToolProperties.DARK_CHAINSAW_2 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw2", 3, 8, 8, 25, false, EnumRarity.COMMON);
        RFToolProperties.DARK_CHAINSAW_2 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw2", 1000000, 1600, 2000); //625 uses
        ToolProperties.DARK_CHAINSAW_3 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw3", 5, 10, 9, 25, false, EnumRarity.UNCOMMON);
        RFToolProperties.DARK_CHAINSAW_3 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw3", 5000000, 2000, 5000); //2500 uses

        if(config.hasChanged())
            config.save();
    }

    private static ToolProperties getToolProperties(String category, String name, int miningLevel, float efficiency, float damage, int enchant, boolean canBreak, EnumRarity rarity){
        miningLevel = config.get(getFullCategoryPath(category, name), "Mining Level", miningLevel).setLanguageKey("config.miningLevel").setRequiresMcRestart(true).getInt();
        efficiency = (float)config.get(getFullCategoryPath(category, name), "Efficiency", efficiency).setLanguageKey("config.efficiency").setRequiresMcRestart(true).getDouble();
        damage = (float)config.get(getFullCategoryPath(category, name), "Damage", damage).setLanguageKey("config.damage").setRequiresMcRestart(true).getDouble();
        enchant = config.get(getFullCategoryPath(category, name), "Enchantability", enchant).setLanguageKey("config.enchant").setRequiresMcRestart(true).getInt();
        canBreak = config.get(getFullCategoryPath(category, name), "Can Break", canBreak).setLanguageKey("config.canBreak").setRequiresMcRestart(true).getBoolean();

        Item.ToolMaterial material = EnumHelper.addToolMaterial(OmniTools.MOD_ID + ":" + name.toUpperCase(Locale.ROOT), miningLevel, 9000, efficiency, damage, enchant);
        return new ToolProperties(material, canBreak, rarity);
    }

    private static RFToolProperties getToolPropertiesRF(String category, String name, int maxEnergy, int energyPerBlock, int rechargeRate){
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
