package goldenapple.omnitools.item;

import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class ItemRFChainsaw extends ItemChainsaw implements IRFContainerItem {
    private RFToolProperties propertiesRF;

    public ItemRFChainsaw(ToolProperties properties, RFToolProperties propertiesRF, float speed) {
        super(properties, speed);
        this.propertiesRF = propertiesRF;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState state, BlockPos pos, EntityLivingBase entity) {
        if(state.getBlockHardness(world, pos) != 0 || (canShear(stack) && state.getBlock() instanceof IShearable))
            drainEnergy(stack, getEnergyUsage(stack, state));
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        drainEnergy(stack, getEnergyUsage(stack) * 2);
        return true;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand) {
        if(shearEntity(stack, player, entity)){
            player.swingArm(hand);
            drainEnergy(stack, getEnergyUsage(stack));
            return true;
        }
        return false;
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
        if(canShear(stack) && state.getBlock() instanceof IShearable)
            return getEnergyUsage(stack) / 5;
        else
            return getEnergyUsage(stack);
    }

    @Override
    public boolean canMine(ItemStack stack) {
        return getEnergyStored(stack) > 0;
    }

    /** IRFTool **/

    @Override
    public ItemStack setEnergy(ItemStack stack, int energy){
        if(!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setInteger("Energy", Math.min(energy, getMaxEnergyStored(stack)));
        return stack;
    }

    @Override
    public ItemStack drainEnergy(ItemStack stack, int energy){
        return setEnergy(stack, Math.max(getEnergyStored(stack) - energy, 0));
    }

    /** IEnergyContainerItem **/

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
            stack.setTagCompound(new NBTTagCompound());

        if(stack.getTagCompound().hasKey("Energy"))
            return stack.getTagCompound().getInteger("Energy");
        else
            return 0;
    }

    @Override
    public int getMaxEnergyStored(ItemStack stack) {
        return propertiesRF.maxEnergy;
    }

}
