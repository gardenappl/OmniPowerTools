package goldenapple.omnitools.client;

import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.item.ITool;
import goldenapple.omnitools.util.MiscUtil;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerRenderHandler {

    /** 3rd person rendering */

    //Note: I can't use RenderPlayerEvent because the arm poses are calculated AFTER that event is fired.
    //So instead I use RenderLivingEvent
    @SubscribeEvent
    public void onLivingRender(RenderLivingEvent.Pre event){
        if (Config.miningAnimation != 1 || !(event.getEntity() instanceof EntityPlayer) || !(event.getRenderer() instanceof RenderPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.getEntity();
        if(player.isSwingInProgress && player.getHeldItem(player.swingingHand) != null) {
            ItemStack stack = player.getHeldItem(player.swingingHand);

            if (stack.getItem() instanceof ITool && ((ITool) stack.getItem()).hasDrillingAnimation(stack)) {
                RenderPlayer renderer = (RenderPlayer) event.getRenderer();
                if (MiscUtil.getHandSide(player, player.swingingHand) == EnumHandSide.RIGHT)
                    renderer.getMainModel().rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                else
                    renderer.getMainModel().leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
                player.swingProgress = 0;
            }
        }
    }

    /** 1st and 3rd person rendering */

    private boolean doAnimation;

    @SubscribeEvent
    public void onClientTick(TickEvent.PlayerTickEvent event){ //handles both 1st and 3rd person rendering, but in a cheatier way
        if(Config.miningAnimation != 2 || event.side.isServer() || event.phase == TickEvent.Phase.START || event.player == null)
            return;
        EntityPlayer player = event.player;
        if(player.isSwingInProgress && player.getHeldItem(player.swingingHand) != null){
            ItemStack stack = player.getHeldItem(event.player.swingingHand);

            if (stack.getItem() instanceof ITool && ((ITool) stack.getItem()).hasDrillingAnimation(stack)) {
                doAnimation = true;
                event.player.setActiveHand(event.player.swingingHand);
                doAnimation = false;
                event.player.swingProgress = 0; //disable the normal mining swing animation
            }
        }
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent.Start event){
        if(doAnimation && event.getDuration() == 0)
            event.setDuration(1);
    }
}
