package goldenapple.omnitools.client;

import goldenapple.omnitools.item.ITool;
import goldenapple.omnitools.util.PlayerUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerRenderHandler {

    @SubscribeEvent
    public void onLivingRender(RenderLivingEvent.Pre event){
        if (event.getEntity() == null || !(event.getEntity() instanceof EntityPlayer) || !(event.getRenderer() instanceof RenderPlayer))
            return;
        EntityPlayer player = (EntityPlayer)event.getEntity();
        ItemStack stack = player.getHeldItemMainhand();

        if (stack != null && stack.getItem() instanceof ITool && ((ITool) stack.getItem()).hasDrillingAnimation(stack)) {
            if (player.isSwingInProgress) {
                RenderPlayer renderer = (RenderPlayer) event.getRenderer();
                if (PlayerUtil.getHandSide(player, player.swingingHand) == EnumHandSide.RIGHT)
                    renderer.getMainModel().rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                else
                    renderer.getMainModel().leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                player.swingProgress = 0;
            }
        }
    }
}
