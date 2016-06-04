package goldenapple.omnitools.client;

import goldenapple.omnitools.item.ITool;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerRenderHandler {
    @SubscribeEvent
    public void onPlayerRender(RenderPlayerEvent.Pre event){
        if (event.getEntityPlayer() == null || event.getEntityPlayer().getHeldItemMainhand() == null)
            return;
        EnumHand activeHand = event.getEntityPlayer().getActiveHand();
        ItemStack stack = event.getEntityPlayer().getActiveItemStack();

        if (stack != null && stack.getItem() instanceof ITool && stack.getItem().getItemUseAction(stack) == EnumAction.BOW) {
            if (event.getEntityPlayer().isSwingInProgress && event.getEntityPlayer().swingingHand == activeHand) {
                event.getRenderer().getMainModel().rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }
    }
}
