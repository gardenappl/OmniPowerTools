package goldenapple.omnitools.item;

import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class ItemChainsaw extends ItemAxe implements ITool{
    private ToolProperties properties;

    public ItemChainsaw(ToolProperties properties, float speed) {
        super(properties.material, properties.material.getDamageVsEntity(), speed);
        this.properties = properties;
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Block block = state.getBlock();
        if(areShearsEnabled(stack)) {
            if(block == Blocks.WEB || state.getMaterial() == Material.LEAVES)
                return 15f;
            if(block == Blocks.WOOL)
                return 5.0f;
        }
        return super.getStrVsBlock(stack, state);
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if(areShearsEnabled(stack))
            return Items.SHEARS.itemInteractionForEntity(stack, player, entity, hand);
        else
            return false;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {
        if(areShearsEnabled(stack))
            return Items.SHEARS.onBlockStartBreak(stack, pos, player);
        else
            return false;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.BOW; //for mining animation
    }

    private boolean areShearsEnabled(ItemStack stack){
        return true;
    }

    /** ITool implementation */

    @Override
    public ToolProperties getProperties(ItemStack stack) {
        return properties;
    }

}
