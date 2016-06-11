package goldenapple.omnitools.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;

public class MiscUtil {
    public static EnumHandSide getHandSide(EntityLivingBase player, EnumHand hand){
        if(hand == EnumHand.MAIN_HAND)
            return player.getPrimaryHand();
        else
            return player.getPrimaryHand() == EnumHandSide.RIGHT ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
    }
}
