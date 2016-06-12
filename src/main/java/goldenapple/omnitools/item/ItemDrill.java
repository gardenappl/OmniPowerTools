package goldenapple.omnitools.item;

import com.google.common.collect.*;
import goldenapple.omnitools.config.Config;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.util.ToolHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Set;

@SuppressWarnings("NullableProblems")
public class ItemDrill extends ItemTool implements ITool {
    public static final Set<Block> effectiveBlocks = Sets.newHashSet();
    private static final Set<Material> effectiveMaterials = Sets.newHashSet(Material.ROCK, Material.IRON, Material.ANVIL);
    protected ToolProperties properties;

    public ItemDrill(ToolProperties properties) {
        super(0f, Config.drillAttackSpeed, properties.material, effectiveBlocks);
        this.properties = properties;
        this.setHarvestLevel("pickaxe", properties.material.getHarvestLevel());
        this.setHarvestLevel("shovel", properties.material.getHarvestLevel());
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("pickaxe", "shovel");
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        if (!canMine(stack))
            return 1.0f;
        if (effectiveMaterials.contains(state.getMaterial()))
            return efficiencyOnProperMaterial;
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
    public boolean onBlockStartBreak(ItemStack stack, BlockPos originPos, EntityPlayer player) {
        for(BlockPos pos : getAoEBlocks(stack, player.worldObj, player, originPos, true))
            ToolHelper.breakBlock(stack, player.worldObj, player.worldObj.getBlockState(pos), pos, player);
        return false;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (!properties.canBreak && damage >= getMaxDamage(stack) - getDamage(stack))
            return;
        super.setDamage(stack, damage);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        if (hasDrillingAnimation(stack) && Config.miningAnimation == 2)
            return EnumAction.BOW;
        return EnumAction.NONE;
    }

    public boolean canMine(ItemStack stack) {
        return !(!properties.canBreak && stack.getItemDamage() == stack.getMaxDamage());
    }

    /** ITool */

    @Override
    public boolean hasDrillingAnimation(ItemStack stack) {
        return canMine(stack);
    }

    @Override
    public boolean hasAoE(ItemStack stack, EntityPlayer player) {
//        return canMine(stack) && getMode(stack) != MiningMode.NORMAL;
        return false;
    }

    @Override
    public ImmutableList<BlockPos> getAoEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos pos, boolean harvest) {
//        if (!hasAoE(stack, player))
            return ImmutableList.of();
//        RayTraceResult mop = rayTrace(world, player, false);
//        if (mop == null)
//            return ImmutableList.of();
//        return getMode(stack).getAoEBlocks(stack, world, player, pos, mop.sideHit, harvest);
    }
}
