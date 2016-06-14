package goldenapple.omnitools.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import goldenapple.omnitools.OmniTools;
import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

@SuppressWarnings("NullableProblems")
public class ItemChainsaw extends ItemAxe implements ITool{
    protected ToolProperties properties;

    public ItemChainsaw(ToolProperties properties, float speed) {
        super(properties.material, properties.material.getDamageVsEntity(), speed);
        this.properties = properties;
        setCreativeTab(OmniTools.creativeTab);
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
    public EnumRarity getRarity(ItemStack stack) {
        return properties.rarity;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if(!properties.canBreak && damage >= getMaxDamage(stack) - getDamage(stack))
            return;
        super.setDamage(stack, damage);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if(state.getBlock() instanceof IShearable)
            stack.damageItem(1, entity);
        else
            super.onBlockDestroyed(stack, world, state, pos, entity);
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if(canShear(stack) && ToolHelper.shearEntity(stack, player, entity)){
            stack.damageItem(1, player);
            return true;
        }
        return false;
    }

//    @Override
//    public boolean canHarvestBlock(IBlockState state){ //stolen from ItemShears
//        Block block = state.getBlock();
//        return block == Blocks.WEB || block == Blocks.REDSTONE_WIRE || block == Blocks.TRIPWIRE;
//    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if(canShear(stack))
            ToolHelper.shearBlock(stack, pos, player);
        return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if(hasDrillingAnimation(stack) && Config.miningAnimation == 2)
            return EnumAction.BOW;
        return EnumAction.NONE;
    }

    public boolean canMine(ItemStack stack){
        return !(!properties.canBreak && stack.getItemDamage() == stack.getMaxDamage());
    }

    public boolean canShear(ItemStack stack){
        return canMine(stack);
    }

    /** ITool */

    @Override
    public boolean hasDrillingAnimation(ItemStack stack) {
        return canMine(stack);
    }

    @Override
    public boolean hasAoE(ItemStack stack, EntityPlayer player) {
        return false;
    }

    @Override
    public ImmutableList<BlockPos> getAoEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos origin, boolean harvest) {
        return ImmutableList.of();
    }
}
