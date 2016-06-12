package goldenapple.omnitools.proxy;

import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.client.BlockRenderHandler;
import goldenapple.omnitools.client.PlayerRenderHandler;
import goldenapple.omnitools.init.ModItems;
import goldenapple.omnitools.reference.Names;
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
        registerMetadataRender(ModItems.materialEIO, Names.MATERIALS_EIO);
        registerRender(ModItems.darkSteelChainsaw1);
        registerRender(ModItems.darkSteelDrill1);
        registerRender(ModItems.darkSteelDrill2, ModItems.darkSteelDrill1.getRegistryName());
        registerRender(ModItems.darkSteelDrill3, ModItems.darkSteelDrill1.getRegistryName());
    }

    private static void registerRender(Item item) {
        registerRender(item, item.getRegistryName());
    }

    private static void registerRender(Item item, ResourceLocation location){
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(location, "inventory"));
    }

    private static void registerMetadataRender(Item item, String[] names){
        for(int i = 0; i < names.length; i++)
            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(new ResourceLocation(OmniTools.MOD_ID, names[i]), "inventory"));
    }
}
