package goldenapple.omnitools.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.IShearable;

import java.util.List;
import java.util.Random;

public class ItemChainsaw extends ItemAxe implements ITool{
    private ToolProperties properties;

    public ItemChainsaw(ToolProperties properties, float speed) {
        super(properties.material, properties.material.getDamageVsEntity(), speed);
        this.properties = properties;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        if(!canMine(stack))
            return 1.0f;
        Block block = state.getBlock();
        if(canShear(stack)) {
            if(block == Blocks.WEB || state.getMaterial() == Material.LEAVES)
                return 15f;
            if(block == Blocks.WOOL)
                return 5.0f;
        }
        return super.getStrVsBlock(stack, state);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        return canMine(stack) ? super.getHarvestLevel(stack, toolClass) : -1;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        return canMine(stack) ? super.getAttributeModifiers(slot, stack) : HashMultimap.<String, AttributeModifier>create();
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if(!getProperties(stack).canBreak && damage >= getMaxDamage(stack) - getDamage(stack))
            return;
        super.setDamage(stack, damage);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if(shearEntity(stack, player, entity)){
            player.swingArm(hand);
            stack.damageItem(1, player);
            return true;
        }
        return false;
    }

    protected boolean shearEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity){
        if (entity.worldObj.isRemote || !canShear(stack))
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

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if(harvestShearableBlock(stack, pos, player))
            stack.damageItem(1, player);
        return false;
    }

    protected boolean harvestShearableBlock(ItemStack stack, BlockPos pos, EntityPlayer player){
        if (player.worldObj.isRemote || player.capabilities.isCreativeMode || !canShear(stack))
            return false;

        Block block = player.worldObj.getBlockState(pos).getBlock();
        if (block instanceof IShearable) {
            IShearable target = (IShearable)block;
            if (target.isShearable(stack, player.worldObj, pos)) {
                List<ItemStack> drops = target.onSheared(stack, player.worldObj, pos, EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack));
                Random rand = new Random();

                for(ItemStack drop : drops) {
                    float f = 0.7F;
                    double d  = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d1 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    double d2 = (double)(rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
                    EntityItem entityItem = new EntityItem(player.worldObj, (double)pos.getX() + d, (double)pos.getY() + d1, (double)pos.getZ() + d2, drop);
                    entityItem.setDefaultPickupDelay();
                    player.worldObj.spawnEntityInWorld(entityItem);
                }
                player.addStat(StatList.getBlockStats(block));
                return true;
            }
        }
        return false;
    }

    private boolean canMine(ItemStack stack){
        return !(!getProperties(stack).canBreak && stack.getItemDamage() == stack.getMaxDamage());
    }

    private boolean canShear(ItemStack stack){
        return canMine(stack);
    }

    /** ITool **/

    @Override
    public ToolProperties getProperties(ItemStack stack) {
        return properties;
    }

    @Override
    public boolean hasDrillingAnimation(ItemStack stack) {
        return true;
    }

}
