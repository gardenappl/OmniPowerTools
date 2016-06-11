package goldenapple.omnitools.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITool {
    public boolean hasDrillingAnimation(ItemStack stack);

    public boolean hasAoE(ItemStack stack, EntityPlayer player);

    public ImmutableList<BlockPos> getAoEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin, boolean harvest);
}
