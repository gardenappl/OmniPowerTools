package goldenapple.omnitools.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class ItemDrill extends ItemTool implements ITool {
    //Stolen from ItemPickaxe and ItemSpade
    private static final Set<Block> effectiveBlocks = Sets.newHashSet(Blocks.ACTIVATOR_RAIL, Blocks.COAL_ORE, Blocks.COBBLESTONE, Blocks.DETECTOR_RAIL, Blocks.DIAMOND_BLOCK, Blocks.DIAMOND_ORE, Blocks.DOUBLE_STONE_SLAB, Blocks.GOLDEN_RAIL, Blocks.GOLD_BLOCK, Blocks.GOLD_ORE, Blocks.ICE, Blocks.IRON_BLOCK, Blocks.IRON_ORE, Blocks.LAPIS_BLOCK, Blocks.LAPIS_ORE, Blocks.LIT_REDSTONE_ORE, Blocks.MOSSY_COBBLESTONE, Blocks.NETHERRACK, Blocks.PACKED_ICE, Blocks.RAIL, Blocks.REDSTONE_ORE, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.STONE, Blocks.STONE_SLAB, Blocks.STONE_BUTTON, Blocks.STONE_PRESSURE_PLATE, Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND, Blocks.GRASS_PATH);
    private static final Set<Material> effectiveMaterials = Sets.newHashSet(Material.ROCK, Material.IRON, Material.ANVIL);
    private ToolProperties properties;

    public ItemDrill(ToolProperties properties) {
        super(1.0f, -2.6f, properties.material, effectiveBlocks);
        this.properties = properties;
        this.setHarvestLevel("pickaxe", properties.material.getHarvestLevel());
        this.setHarvestLevel("shovel", properties.material.getHarvestLevel());
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return ImmutableSet.of("pickaxe", "shovel");
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state) {
        if(!canMine(stack))
            return 1.0f;
        if(effectiveMaterials.contains(state.getMaterial()))
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
    public void setDamage(ItemStack stack, int damage) {
        if(!properties.canBreak && damage >= getMaxDamage(stack) - getDamage(stack))
            return;
        super.setDamage(stack, damage);
    }

    public boolean canMine(ItemStack stack){
        return !(!properties.canBreak && stack.getItemDamage() == stack.getMaxDamage());
    }

    @Override
    public boolean hasDrillingAnimation(ItemStack stack) {
        return canMine(stack);
    }
}
