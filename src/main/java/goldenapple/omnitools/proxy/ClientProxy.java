package goldenapple.omnitools.proxy;

import goldenapple.omnitools.client.BlockRenderHandler;
import goldenapple.omnitools.client.PlayerRenderHandler;
import goldenapple.omnitools.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends Proxy {
    @Override
    public void init() {
        MinecraftForge.EVENT_BUS.register(new PlayerRenderHandler());

        BlockRenderHandler blockRenderHandler = new BlockRenderHandler();
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(blockRenderHandler);
        MinecraftForge.EVENT_BUS.register(blockRenderHandler);
    }

    @Override
    public void registerRenders(){
        registerRender(ModItems.darkSteelChainsaw);
        registerRender(ModItems.darkSteelDrill);
    }

    private static void registerRender(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    private static void registerMetadataRender(Item item, String[] names){
        for(int i = 0; i < names.length; i++)
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation("advaccessories", names[i]), "inventory"));
    }
}
