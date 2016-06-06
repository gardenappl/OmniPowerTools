package goldenapple.omnitools.client.gui;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.ArrayList;
import java.util.List;

public class GuiOmniToolsConfig extends GuiConfig {
    public GuiOmniToolsConfig(GuiScreen parentScreen) {
        super(parentScreen, getConfigElements(), OmniTools.MOD_ID, false, false, getAbridgedConfigPath(Config.config.toString()));
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        list.add(new ConfigElement(Config.config.getCategory(Config.CATEGORY_ENDERIO)));

        return list;
    }
}
