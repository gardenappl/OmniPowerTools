package goldenapple.omnitools.config;

import com.google.common.collect.ImmutableList;
import goldenapple.omnitools.util.ToolHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class MiningMode {
    public static final MiningMode NORMAL = new MiningMode(0, 0, 0);

    public final int width;
    public final int height;
    public final int depth;

    public MiningMode(int width, int height, int depth){
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    @Override
    public String toString() {
        return String.format("%sx%sx%s", 1 + width * 2, 1 + height * 2, 1 + depth * 2);
    }

    public ImmutableList<BlockPos> getAoEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos pos, EnumFacing side, boolean harvest){
        if(harvest && !ForgeHooks.canHarvestBlock(world.getBlockState(pos).getBlock(), player, world, pos))
            return ImmutableList.of();
        int xRadius, yRadius, zRadius;
        BlockPos center = pos.offset(side.getOpposite(), depth);

        if (side.getAxis() == EnumFacing.Axis.Y) {
            xRadius = player.getHorizontalFacing().getAxis() == EnumFacing.Axis.X ? height : width;
            yRadius = depth;
            zRadius = player.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z ? height : width;
        } else {
            xRadius = player.getHorizontalFacing().getAxis() == EnumFacing.Axis.X ? depth : width;
            yRadius = height;
            zRadius = player.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z ? depth : width;
        }

        ImmutableList.Builder<BlockPos> builder = ImmutableList.builder();
        for (int x = center.getX() - xRadius; x <= center.getX() + xRadius; x++) {
            for (int y = center.getY() - yRadius; y <= center.getY() + yRadius; y++) {
                for (int z = center.getZ() - zRadius; z <= center.getZ() + zRadius; z++) {
                    BlockPos harvestPos = new BlockPos(x, y, z);
                    if(harvestPos.equals(pos))
                        continue;
                    IBlockState state = world.getBlockState(harvestPos);
                    if(!harvest || ToolHelper.canAoEHarvest(world, state, harvestPos, pos, player))
                        builder.add(new BlockPos(x, y, z));
                }
            }
        }
        return builder.build();
    }

}
