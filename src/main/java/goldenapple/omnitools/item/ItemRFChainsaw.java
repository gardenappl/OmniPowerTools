package goldenapple.omnitools.item;

import cofh.api.energy.IEnergyContainerItem;
import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemRFChainsaw extends ItemChainsaw implements IEnergyContainerItem, IRFTool{
    private RFToolProperties propertiesRF;

    public ItemRFChainsaw(ToolProperties properties, RFToolProperties propertiesRF, float speed) {
        super(properties, speed);
        this.propertiesRF = propertiesRF;
    }

    /** IRFTool **/

    @Override
    public RFToolProperties getPropertiesRF(ItemStack stack) {
        return propertiesRF;
    }

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
