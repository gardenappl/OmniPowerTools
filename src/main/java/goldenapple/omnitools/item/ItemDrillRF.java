package goldenapple.omnitools.item;

import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nonnull;
import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemDrillRF extends ItemDrill implements IRFContainerItem {
    protected RFToolProperties propertiesRF;

    public ItemDrillRF(ToolProperties properties, RFToolProperties propertiesRF) {
        super(properties);
        this.propertiesRF = propertiesRF;
        this.setHasSubtypes(true);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if(state.getBlockHardness(world, pos) != 0)
            drainEnergy(stack, getEnergyUsage(stack, state), entity);
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        drainEnergy(stack, getEnergyUsage(stack) * 2, attacker);
        return true;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(item));
        list.add(setEnergy(new ItemStack(item), propertiesRF.maxEnergy));
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        if (!stack.hasTagCompound())
            return true;
        return !stack.getTagCompound().getBoolean("CreativeTabIcon");
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0d - (double) getEnergyStored(stack) / (double) getMaxEnergyStored(stack);
    }

    @Override
    public boolean isItemTool(ItemStack stack) {
        return true;
    }

    @Override
    public int getMaxDamage() {
        return 0;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    public int getEnergyUsage(ItemStack stack){
        //Vanilla formula: a 100% / (unbreaking level + 1) chance to not take damage
        return Math.round(propertiesRF.energyPerBlock / (EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) + 1));
    }

    public int getEnergyUsage(ItemStack stack, IBlockState state){
        return getEnergyUsage(stack);
    }

    @Override
    public boolean canMine(ItemStack stack) {
        return getEnergyStored(stack) > 0;
    }

    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return super.getItemUseAction(stack);
    }

    /** IRFTool */

    @Override
    public ItemStack setEnergy(ItemStack stack, int energy){
        stack.setTagInfo("Energy", new NBTTagInt(Math.min(energy, getMaxEnergyStored(stack))));
        return stack;
    }

    @Override
    public ItemStack drainEnergy(ItemStack stack, int energy, EntityLivingBase entity){
        if(entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.isCreativeMode)
            return stack;
        setEnergy(stack, Math.max(getEnergyStored(stack) - energy, 0));
        if(getEnergyStored(stack) == 0 && properties.canBreak) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                ForgeEventFactory.onPlayerDestroyItem(player, stack, entity.getActiveHand());
                player.addStat(StatList.getObjectBreakStats(this));
            }
            entity.renderBrokenItemStack(stack);
            --stack.stackSize;
            stack.setItemDamage(0);
        }
        return stack;
    }

    /** IEnergyContainerItem */

    @Override
    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {
        int energy = getEnergyStored(stack);
        int energyReceived = Math.min(propertiesRF.maxEnergy - energy, Math.min(propertiesRF.rechargeRate, maxReceive));

        if (!simulate)
            setEnergy(stack, energy + energyReceived);
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored(ItemStack stack) {
        if(!stack.hasTagCompound())
            return 0;
        return stack.getTagCompound().getInteger("Energy");
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return propertiesRF.maxEnergy;
    }

}
