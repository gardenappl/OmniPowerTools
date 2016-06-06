package goldenapple.omnitools.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class PlayerUtil {
    public static EnumHandSide getHandSide(EntityPlayer player, EnumHand hand){
        if(hand == EnumHand.MAIN_HAND)
            return player.getPrimaryHand();
        else
            return player.getPrimaryHand() == EnumHandSide.RIGHT ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
    }
}
