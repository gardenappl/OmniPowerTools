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

        /** Values from original RFDrills */
//        ToolTier.DRILL1 = getToolTierInfo("drill_tier1", 20000, 80, 80, EnumRarity.common, true, false, 2, 6.0F, 2.0F, 0);
//        ToolTier.DRILL2 = getToolTierInfo("drill_tier2", 100000, 200, 400, EnumRarity.common, false, false, 3, 8.0F, 3.0F, 0);
//        ToolTier.DRILL3 = getToolTierInfo("drill_tier3", 1000000, 800, 1500, EnumRarity.uncommon, false, true, 3, 10.0F, 4.0F, 10);
//        ToolTier.DRILL4 = getToolTierInfo("drill_tier4", 5000000, 1600, 5000, EnumRarity.rare, false, true, 4, 12.0F, 5.0F, 15);

        ToolProperties.DARK_DRILL_1 = getToolProperties(CATEGORY_ENDERIO, "dark_drill1", 2, 6, 3, 25, true, EnumRarity.COMMON);
        RFToolProperties.DARK_DRILL_1 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill1", 200_000, 800, 500); //125 uses
        ToolProperties.DARK_DRILL_2 = getToolProperties(CATEGORY_ENDERIO, "dark_drill2", 3, 8, 4, 25, false, EnumRarity.COMMON);
        RFToolProperties.DARK_DRILL_2 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill2", 500_000, 1000, 1200); //500 uses
        ToolProperties.DARK_DRILL_3 = getToolProperties(CATEGORY_ENDERIO, "dark_drill3", 4, 10, 5, 25, false, EnumRarity.UNCOMMON);
        RFToolProperties.DARK_DRILL_3 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill3", 1_500_000, 1200, 3000); //1250 uses
        ToolProperties.DARK_DRILL_4 = getToolProperties(CATEGORY_ENDERIO, "dark_drill4", 5, 12, 6, 25, false, EnumRarity.RARE);
        RFToolProperties.DARK_DRILL_4 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_drill4", 5_000_000, 1600, 5000); //3125 uses

        ToolProperties.DARK_CHAINSAW_1 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw1", 2, 6, 7, 25, true, EnumRarity.COMMON);
        RFToolProperties.DARK_CHAINSAW_1 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw1", 200_000, 800, 500); //125 uses
        ToolProperties.DARK_CHAINSAW_2 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw2", 3, 8, 8, 25, false, EnumRarity.COMMON);
        RFToolProperties.DARK_CHAINSAW_2 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw2", 500_000, 1000, 1200); //500 uses
        ToolProperties.DARK_CHAINSAW_3 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw3", 4, 10, 9, 25, false, EnumRarity.UNCOMMON);
        RFToolProperties.DARK_CHAINSAW_3 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw3", 1_500_000, 1200, 3000); //1250 uses
        ToolProperties.DARK_CHAINSAW_4 = getToolProperties(CATEGORY_ENDERIO, "dark_chainsaw4", 5, 12, 10, 25, false, EnumRarity.RARE);
        RFToolProperties.DARK_CHAINSAW_4 = getToolPropertiesRF(CATEGORY_ENDERIO, "dark_chainsaw4", 5_000_000, 1600, 5000); //3125 uses

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
