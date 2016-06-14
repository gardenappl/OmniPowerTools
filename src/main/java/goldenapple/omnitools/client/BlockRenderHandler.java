package goldenapple.omnitools.client;

import com.google.common.collect.ImmutableList;
import goldenapple.omnitools.item.ITool;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.List;

//Stolen straight from Tinker's Construct https://github.com/SlimeKnights/TinkersConstruct/blob/master/src/main/java/slimeknights/tconstruct/tools/client/RenderEvents.java
public class BlockRenderHandler implements IResourceManagerReloadListener {

    private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];

    @SubscribeEvent
    public void renderExtraBlockHighlight(DrawBlockHighlightEvent event){
        PlayerControllerMP controllerMP = Minecraft.getMinecraft().playerController;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        World world = player.worldObj;
        ItemStack stack = player.getHeldItemMainhand();

        if(stack != null && stack.getItem() instanceof ITool && ((ITool) stack.getItem()).hasAoE(stack, player)) {
            RayTraceResult mop = player.rayTrace(controllerMP.getBlockReachDistance(), event.getPartialTicks());
            if (mop != null) {
                ImmutableList<BlockPos> extraBlocks = ((ITool) stack.getItem()).getAoEBlocks(stack, world, player, mop.getBlockPos(), false);
                for (BlockPos pos : extraBlocks)
                    event.getContext().drawSelectionBox(player, new RayTraceResult(mop.hitVec.add(new Vec3d(pos.subtract(controllerMP.currentBlock))), mop.sideHit, pos), 0, event.getPartialTicks());
            }
        }
    }

    @SubscribeEvent
    public void renderExtraBlockBreak(RenderWorldLastEvent event) {
        PlayerControllerMP controllerMP = Minecraft.getMinecraft().playerController;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        World world = player.worldObj;
        ItemStack stack = player.getHeldItemMainhand();

        if(stack != null && stack.getItem() instanceof ITool && ((ITool) stack.getItem()).hasAoE(stack, player)) {
            if (controllerMP.isHittingBlock) {
                if (controllerMP.currentItemHittingBlock != null && controllerMP.currentItemHittingBlock.getItem() instanceof ITool) {
                    BlockPos pos = controllerMP.currentBlock;
                    drawBlockDamageTexture(player, event.getPartialTicks(), world, ((ITool) stack.getItem()).getAoEBlocks(stack, world, player, pos, true));
                }
            }
        }
    }

    /** RenderGlobal.drawBlockDamageTexture */
    public void drawBlockDamageTexture(Entity entity, float partialTicks, World world, List<BlockPos> blocks){
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexBuffer = tessellator.getBuffer();
        TextureManager renderEngine = Minecraft.getMinecraft().renderEngine;

        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;

        int progress = (int)(Minecraft.getMinecraft().playerController.curBlockDamageMP * 10f) - 1; // from 0 to 10

        if(progress < 0)
            return;

        renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        //preRenderDamagedBlocks BEGIN
        GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.doPolygonOffset(-3.0F, -3.0F);
        GlStateManager.enablePolygonOffset();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableAlpha();
        GlStateManager.pushMatrix();
        //preRenderDamagedBlocks END

        vertexBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
        vertexBuffer.setTranslation(-d0, -d1, -d2);
        vertexBuffer.noColor();

        for(BlockPos pos : blocks) {
            TileEntity te = world.getTileEntity(pos);
            boolean hasBreak = te != null && te.canRenderBreaking();

            if (!hasBreak){
                IBlockState iblockstate = world.getBlockState(pos);
                if (iblockstate.getMaterial() != Material.AIR){
                    TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[progress];
                    BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
                    blockrendererdispatcher.renderBlockDamage(iblockstate, pos, textureatlassprite, world);
                }
            }
        }

        tessellator.draw();
        vertexBuffer.setTranslation(0.0D, 0.0D, 0.0D);
        // postRenderDamagedBlocks BEGIN
        GlStateManager.disableAlpha();
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.enableAlpha();
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
        // postRenderDamagedBlocks END
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();

        for (int i = 0; i < this.destroyBlockIcons.length; ++i)
            this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
    }
}
