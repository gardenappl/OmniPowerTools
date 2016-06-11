package goldenapple.omnitools.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class ToolHelper {
    public static boolean shearEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity){ //stolen from ItemShears
        if (entity.worldObj.isRemote)
            return false;

        if (entity instanceof IShearable) {
            IShearable target = (IShearable)entity;
            BlockPos pos = new BlockPos(entity.posX, entity.posY, entity.posZ);
            if (target.isShearable(stack, entity.worldObj, pos)) {
                List<ItemStack> drops = target.onSheared(stack, entity.worldObj, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));

                Random rand = new Random();
                for(ItemStack drop : drops) {
                    EntityItem entityItem = entity.entityDropItem(drop, 1.0F);
                    entityItem.motionY += rand.nextFloat() * 0.05F;
                    entityItem.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    entityItem.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
                return true;
            }
        }
        return false;
    }

    public static boolean shearBlock(ItemStack stack, BlockPos pos, EntityPlayer player){ //stolen from ItemShears
        if (player.worldObj.isRemote || player.capabilities.isCreativeMode)
            return false;

        Block block = player.worldObj.getBlockState(pos).getBlock();
        if (block instanceof IShearable) {
            IShearable target = (IShearable)block;
            if (target.isShearable(stack, player.worldObj, pos)) {
                List<ItemStack> drops = target.onSheared(stack, player.worldObj, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
                for(ItemStack drop : drops) {
                    float f = 0.7F;
                    double x = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double y = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    double z = player.worldObj.rand.nextFloat() * f + (1.0F - f) * 0.5D;
                    EntityItem entityItem = new EntityItem(player.worldObj, pos.getX() + x, pos.getY() + y, pos.getZ() + z, drop);
                    entityItem.setDefaultPickupDelay();
                    player.worldObj.spawnEntityInWorld(entityItem);
                }
                player.addStat(StatList.getBlockStats(block));
                return true;
            }
        }
        return false;
    }

    public static boolean canAoEHarvest(World world, IBlockState state, BlockPos pos, BlockPos originPos, EntityPlayer player){
        if(!ForgeHooks.canHarvestBlock(state.getBlock(), player, world, pos) || world.isAirBlock(pos))
            return false;
        float strength = ForgeHooks.blockStrength(state, player, world, pos);
        float originStrength = ForgeHooks.blockStrength(world.getBlockState(originPos), player, world, originPos);
        return strength != 0 && originStrength / strength < 10f;
    }

    //Stolen from Tinker's Construct https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/library/utils/ToolHelper.java
    public static void breakBlock(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityPlayer player) {
        Block block = state.getBlock();
        if (player.capabilities.isCreativeMode) {
            block.onBlockHarvested(world, pos, state, player);
            if (block.removedByPlayer(state, world, pos, player, false))
                block.onBlockDestroyedByPlayer(world, pos, state);

            if (!world.isRemote)
                ((EntityPlayerMP)player).connection.sendPacket(new SPacketBlockChange(world, pos));
            return;
        }
        stack.onBlockDestroyed(world, state, pos, player);

        if (!world.isRemote) { // server sided handling
            int xp = ForgeHooks.onBlockBreakEvent(world, ((EntityPlayerMP) player).interactionManager.getGameType(), (EntityPlayerMP) player, pos);
            if(xp == -1)  //event cancelled
                return;

            // serverside we reproduce ItemInWorldManager.tryHarvestBlock

            // ItemInWorldManager.removeBlock
            block.onBlockHarvested(world, pos, state, player);

            if(block.removedByPlayer(state, world, pos, player, true)){
                block.onBlockDestroyedByPlayer(world, pos, state);
                block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);
                block.dropXpOnBlockBreak(world, pos, xp);
            }

            EntityPlayerMP mpPlayer = (EntityPlayerMP) player;
            mpPlayer.connection.sendPacket(new SPacketBlockChange(world, pos));
        }
        else { // client sided handling
            // PlayerControllerMP.onPlayerDestroyBlock

            world.playBroadcastSound(2001, pos, Block.getStateId(state));
            if(block.removedByPlayer(state, world, pos, player, true))
                block.onBlockDestroyedByPlayer(world, pos, state);
            stack.onBlockDestroyed(world, state, pos, player);

            Minecraft mc = Minecraft.getMinecraft();
            mc.getConnection().sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, mc.objectMouseOver.sideHit));
        }
    }
}
