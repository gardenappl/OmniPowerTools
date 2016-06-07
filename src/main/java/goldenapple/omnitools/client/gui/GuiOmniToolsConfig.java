package goldenapple.omnitools.client.gui;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

import static goldenapple.omnitools.config.Config.config;

public class GuiOmniToolsConfig extends GuiConfig {
    public GuiOmniToolsConfig(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), OmniTools.MOD_ID, false, false, getAbridgedConfigPath(config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<>();

        list.add(new ConfigElement(config.getCategory(Config.CATEGORY_ENDERIO)));
        for(Property property : config.getCategory(Configuration.CATEGORY_GENERAL).getOrderedValues())
            list.add(new ConfigElement(property));

        return list;
    }
}
