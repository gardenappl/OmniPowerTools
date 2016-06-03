package goldenapple.multitools;

import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(name = PowerMultitools.MOD_NAME, modid = PowerMultitools.MOD_ID, version = PowerMultitools.VERSION)
public class PowerMultitools {
    public static final String MOD_NAME = "Power Multitools";
    public static final String MOD_ID = "power_multitools";
    public static final String VERSION = "0.1";
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        System.out.println("DIRT BLOCK >> " + Blocks.DIRT.getUnlocalizedName());
    }
}
