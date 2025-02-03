package com.delu.ancienttimes.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BrushItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DiamondChisel extends BrushItem {
    // Fabric Settings == Forge Properties
    public DiamondChisel(Settings pProperties) {
        super(pProperties);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    // Fabric ActionResult == Forge InteractionResult
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        // Fabric World == Forge Level
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState blockState = world.getBlockState(pos);

        Block targgetBlock = ModBlocks.SUS_ANDESITE;

        if (blockState.getBlock() == targgetBlock) {
            PlayerEntity player = context.getPlayer();
            if (player != null) {
                // Fabric setCurrentHand() == Forge startUsingItem()
                player.setCurrentHand(context.getHand());
            }
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
}
