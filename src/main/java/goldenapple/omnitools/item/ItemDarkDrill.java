package goldenapple.omnitools.item;

import com.google.common.collect.ImmutableList;
import goldenapple.omnitools.config.MiningMode;
import goldenapple.omnitools.config.RFToolProperties;
import goldenapple.omnitools.config.ToolProperties;
import goldenapple.omnitools.item.upgrade.UpgradeHelper;
import goldenapple.omnitools.item.upgrade.Upgrades;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class ItemDarkDrill extends ItemDrillRF{
    private static ItemDarkDrill[] upgrades = new ItemDarkDrill[4];
    private int level;

    private static final MiningMode BEAST_MODE = new MiningMode(1, 1, 0);

    public ItemDarkDrill(ToolProperties properties, RFToolProperties propertiesRF, int empoweredLevel) {
        super(properties, propertiesRF);
        upgrades[empoweredLevel] = this;
        level = empoweredLevel;
    }

    public static ItemDarkDrill getEmpoweredUpgrade(int level){
        if(level < 0)
            return upgrades[0];
        else if(level >= upgrades.length)
            return upgrades[upgrades.length - 1];
        return upgrades[level];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        if(level <= 0)
            super.getSubItems(item, tab, list);
        else if(level >= upgrades.length - 1){
            list.add(Upgrades.empowered.apply(new ItemStack(item), upgrades.length - 1, false));
            list.add(Upgrades.empowered.apply(setEnergy(new ItemStack(item), propertiesRF.maxEnergy), upgrades.length - 1, false));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
        if(UpgradeHelper.getUpgradeLevel(stack, Upgrades.beastMode) > 0 && player.isSneaking()) {
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_TOUCH, SoundCategory.NEUTRAL, 0.2F, 0.5F * ((itemRand.nextFloat() - itemRand.nextFloat()) * 0.7F + 1.8F));
            return new ActionResult<>(EnumActionResult.SUCCESS, changeMode(stack));
        }
        else
            return new ActionResult<>(EnumActionResult.PASS, stack);
    }

    public ItemStack changeMode(ItemStack stack) {
        if(!stack.hasTagCompound())
            stack.setTagInfo("Mode", new NBTTagByte((byte) 1));
        else {
            byte mode = stack.getTagCompound().getByte("Mode");
            if(mode < 1)
                stack.setTagInfo("Mode", new NBTTagByte((byte)(mode + 1)));
            else
                stack.setTagInfo("Mode", new NBTTagByte((byte)0));
        }
        return stack;
    }

    public MiningMode getMode(ItemStack stack) {
        if(!stack.hasTagCompound() || stack.getTagCompound().getByte("Mode") == 0)
            return MiningMode.NORMAL;
        else
            return BEAST_MODE;
    }

    @Override
    public boolean hasAoE(ItemStack stack, EntityPlayer player) {
        return canMine(stack) && getMode(stack) != MiningMode.NORMAL;
    }

    @Override
    public ImmutableList<BlockPos> getAoEBlocks(ItemStack stack, World world, EntityPlayer player, BlockPos pos, boolean harvest) {
        if (!hasAoE(stack, player))
            return ImmutableList.of();
        RayTraceResult mop = rayTrace(world, player, false);
        if (mop == null)
            return ImmutableList.of();
        return getMode(stack).getAoEBlocks(stack, world, player, pos, mop.sideHit, harvest);
    }
}
